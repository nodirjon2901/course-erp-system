package org.example.courseerpsystem.service;

import lombok.RequiredArgsConstructor;
import org.example.courseerpsystem.domain.dto.request.GroupCreateDTO;
import org.example.courseerpsystem.domain.dto.response.GroupResponseDTO;
import org.example.courseerpsystem.domain.entity.GroupEntity;
import org.example.courseerpsystem.domain.entity.UserEntity;
import org.example.courseerpsystem.domain.enums.UserRole;
import org.example.courseerpsystem.exception.AlreadyExistsException;
import org.example.courseerpsystem.exception.DataNotFoundException;
import org.example.courseerpsystem.repository.CourseRepository;
import org.example.courseerpsystem.repository.GroupRepository;
import org.example.courseerpsystem.repository.MentorInfoRepository;
import org.example.courseerpsystem.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GroupService implements BaseService<GroupResponseDTO, GroupCreateDTO> {

    private final GroupRepository groupRepository;

    private final MentorInfoRepository mentorInfoRepository;

    private final UserRepository userRepository;

    private final CourseRepository courseRepository;

    private final ModelMapper modelMapper;

    @Override
    public void save(GroupCreateDTO groupCreateDTO) {
        if (groupRepository.findByName(groupCreateDTO.getName()).isPresent()) {
            throw new AlreadyExistsException("Group is already exists with this name");
        }
        GroupEntity groupEntity = modelMapper.map(groupCreateDTO, GroupEntity.class);
        groupEntity.setCourse(courseRepository.findById(groupCreateDTO.getCourseId())
                .orElseThrow(()->new DataNotFoundException("Course is not found with this id")));
        groupEntity.setMentor(mentorInfoRepository.findById(groupCreateDTO.getMentorId())
                .orElseThrow(()->new DataNotFoundException("Mentor is not found with this id")));
        groupEntity.setEmpty(true);
        groupRepository.save(groupEntity);
    }

    @Override
    public void delete(UUID id) {
        groupRepository.deleteById(id);
    }

    @Override
    public GroupResponseDTO update(GroupCreateDTO groupCreateDTO, UUID id) {
        GroupEntity groupEntity = groupRepository.findById(id).orElseThrow(() -> new DataNotFoundException("Group is not found with this id"));
        groupEntity.setName(groupCreateDTO.getName());
        groupEntity.setCapacity(groupCreateDTO.getCapacity());
        return modelMapper.map(groupRepository.save(groupEntity), GroupResponseDTO.class);
    }

    @Override
    public GroupResponseDTO findById(UUID id) {
        GroupEntity groupEntity = groupRepository.findById(id).orElseThrow(() -> new DataNotFoundException("Group is not found with this id"));
        return modelMapper.map(groupEntity, GroupResponseDTO.class);
    }

    @Override
    public List<GroupResponseDTO> findAll() {
        return groupRepository.findAll().stream()
                .map(groupEntity -> {
                    return modelMapper.map(groupEntity, GroupResponseDTO.class);
                }).toList();
    }

    public List<GroupResponseDTO> findAllByMentorId(UUID mentorId) {
        return groupRepository.findByMentorId(mentorId).stream()
                .map(groupEntity -> {
                    return modelMapper.map(groupEntity, GroupResponseDTO.class);
                }).toList();
    }

    public List<GroupResponseDTO> findAllByCourseId(UUID courseId) {
        return groupRepository.findByCourseId(courseId).stream()
                .map(groupEntity -> {
                    return modelMapper.map(groupEntity, GroupResponseDTO.class);
                }).toList();
    }

    public GroupResponseDTO addUser(UUID groupId, UUID userId) {
        GroupEntity groupEntity = groupRepository.findById(groupId).orElseThrow(() -> new DataNotFoundException("Group is not found with this id"));
        UserEntity userEntity = userRepository.findById(userId).orElseThrow(() -> new DataNotFoundException("User is not found with this id"));
        if (groupEntity.getUsers().stream()
                .anyMatch(userEntity1 -> Objects.equals(userEntity1.getId(), userEntity.getId()))) {
            throw new AlreadyExistsException("This user is already exists in this group");
        }
        if (!isEmpty(groupEntity.getId())) {
            throw new AlreadyExistsException("This group is already filled");
        }
        userEntity.setRole(UserRole.STUDENT);
        groupEntity.getUsers().add(userRepository.save(userEntity));
        return modelMapper.map(groupRepository.save(groupEntity), GroupResponseDTO.class);
    }

    private boolean isEmpty(UUID id) {
        GroupEntity groupEntity = groupRepository.findById(id).orElseThrow(() -> new DataNotFoundException("Group is not found with this id"));
        return groupEntity.getCapacity() > groupEntity.getUsers().size();
    }
}
