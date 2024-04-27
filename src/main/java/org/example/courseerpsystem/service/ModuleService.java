package org.example.courseerpsystem.service;

import lombok.RequiredArgsConstructor;
import org.example.courseerpsystem.domain.dto.request.ModuleCreateDTO;
import org.example.courseerpsystem.domain.dto.response.ModuleResponseDTO;
import org.example.courseerpsystem.domain.entity.GroupEntity;
import org.example.courseerpsystem.domain.entity.ModuleEntity;
import org.example.courseerpsystem.exception.AlreadyExistsException;
import org.example.courseerpsystem.exception.DataNotFoundException;
import org.example.courseerpsystem.repository.CourseRepository;
import org.example.courseerpsystem.repository.GroupRepository;
import org.example.courseerpsystem.repository.ModuleRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ModuleService implements BaseService<ModuleResponseDTO, ModuleCreateDTO> {

    private final ModuleRepository moduleRepository;

    private final CourseRepository courseRepository;

    private final GroupRepository groupRepository;

    private final ModelMapper modelMapper;

    @Override
    public void save(ModuleCreateDTO moduleCreateDTO) {
        if (moduleRepository.findByNumeric(moduleCreateDTO.getNumeric()).isPresent()) {
            throw new AlreadyExistsException("Module already exists with this number");
        }
        ModuleEntity moduleEntity = modelMapper.map(moduleCreateDTO, ModuleEntity.class);
        moduleEntity.setCourse(courseRepository.findById(moduleCreateDTO.getCourseId()).get());
        moduleRepository.save(moduleEntity);
    }

    @Override
    public void delete(UUID id) {
        moduleRepository.deleteById(id);
    }

    @Override
    public ModuleResponseDTO update(ModuleCreateDTO moduleCreateDTO, UUID id) {
        ModuleEntity moduleEntity = moduleRepository.findById(id).orElseThrow(() -> new DataNotFoundException("Module is not found with this id"));
        moduleEntity.setDescription(moduleCreateDTO.getDescription());
        moduleEntity.setLessonAmount(moduleEntity.getLessonAmount());
        return modelMapper.map(moduleRepository.save(moduleEntity), ModuleResponseDTO.class);
    }

    @Override
    public ModuleResponseDTO findById(UUID id) {
        ModuleEntity moduleEntity = moduleRepository.findById(id).orElseThrow(() -> new DataNotFoundException("Module is not found with this id"));
        return modelMapper.map(moduleEntity, ModuleResponseDTO.class);
    }

    @Override
    public List<ModuleResponseDTO> findAll() {
        return moduleRepository.findAll().stream()
                .map(moduleEntity -> {
                    return modelMapper.map(moduleEntity, ModuleResponseDTO.class);
                }).toList();
    }

    public List<ModuleResponseDTO> findAllByCourseId(UUID courseId) {
        return moduleRepository.findAll().stream()
                .filter(moduleEntity -> Objects.equals(moduleEntity.getCourse().getId(), courseId))
                .map(moduleEntity -> {
                    return modelMapper.map(moduleEntity, ModuleResponseDTO.class);
                }).toList();
    }

    public ModuleResponseDTO addGroup(UUID moduleId, UUID groupId) {
        ModuleEntity moduleEntity = moduleRepository.findById(moduleId).orElseThrow(() -> new DataNotFoundException("Module is not found with this id"));
        GroupEntity groupEntity = groupRepository.findById(groupId).orElseThrow(() -> new DataNotFoundException("Group is not found with this id"));
        if (moduleEntity.getGroups().stream()
                .anyMatch(groupEntity1 -> Objects.equals(groupEntity1.getId(), groupEntity.getId()))) {
            throw new AlreadyExistsException("This group is already exists in this module");
        }
        moduleEntity.getGroups().add(groupEntity);
        return modelMapper.map(moduleRepository.save(moduleEntity), ModuleResponseDTO.class);
    }
}
