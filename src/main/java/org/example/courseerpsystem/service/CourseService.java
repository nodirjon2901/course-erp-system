package org.example.courseerpsystem.service;

import lombok.RequiredArgsConstructor;
import org.example.courseerpsystem.domain.dto.request.CourseCreateDTO;
import org.example.courseerpsystem.domain.dto.response.CourseResponseDTO;
import org.example.courseerpsystem.domain.dto.response.GroupResponseDTO;
import org.example.courseerpsystem.domain.entity.CourseEntity;
import org.example.courseerpsystem.exception.AlreadyExistsException;
import org.example.courseerpsystem.exception.DataNotFoundException;
import org.example.courseerpsystem.repository.CourseRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CourseService implements BaseService<CourseResponseDTO, CourseCreateDTO> {

    private final CourseRepository courseRepository;

    private final ModelMapper modelMapper;

    @Override
    public void save(CourseCreateDTO courseCreateDTO) {
        if (courseRepository.findByName(courseCreateDTO.getName()).isPresent()) {
            throw new AlreadyExistsException("Course is already exists with this name");
        }
        CourseEntity courseEntity = modelMapper.map(courseCreateDTO, CourseEntity.class);
        courseRepository.save(courseEntity);
    }

    @Override
    public void delete(UUID id) {
        courseRepository.deleteById(id);
    }

    @Override
    public CourseResponseDTO update(CourseCreateDTO courseCreateDTO, UUID id) {
        CourseEntity courseEntity = courseRepository.findById(id).orElseThrow(() -> new DataNotFoundException("Course is not found with this id"));
        courseEntity.setDescription(courseEntity.getDescription());
        courseEntity.setPrice(courseEntity.getPrice());
        courseEntity.setNumberOfModules(courseCreateDTO.getNumberOfModules());
        return modelMapper.map(courseRepository.save(courseEntity), CourseResponseDTO.class);
    }

    @Override
    public CourseResponseDTO findById(UUID id) {
        CourseEntity courseEntity = courseRepository.findById(id).orElseThrow(() -> new DataNotFoundException("Course is not found with this id"));
        return modelMapper.map(courseEntity, CourseResponseDTO.class);
    }

    @Override
    public List<CourseResponseDTO> findAll() {
        return courseRepository.findAll().stream()
                .map(courseEntity -> {
                    return modelMapper.map(courseEntity, CourseResponseDTO.class);
                }).toList();
    }

    public CourseResponseDTO findByName(String name) {
        CourseEntity courseEntity = courseRepository.findByName(name).orElseThrow(() -> new DataNotFoundException("Course is not found with this name"));
        return modelMapper.map(courseEntity, CourseResponseDTO.class);
    }

    public List<GroupResponseDTO> findGroupsInCourse(UUID courseId){
        CourseEntity courseEntity = courseRepository.findById(courseId).orElseThrow(() -> new DataNotFoundException("Course is not found with this id"));
        return courseEntity.getGroupList().stream()
                .map(groupEntity -> {
                    return modelMapper.map(groupEntity, GroupResponseDTO.class);
                }).toList();
    }
}
