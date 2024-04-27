package org.example.courseerpsystem.service;

import lombok.RequiredArgsConstructor;
import org.example.courseerpsystem.domain.dto.request.LessonCreateDTO;
import org.example.courseerpsystem.domain.dto.response.LessonResponseDTO;
import org.example.courseerpsystem.domain.entity.LessonEntity;
import org.example.courseerpsystem.domain.enums.LessonStatus;
import org.example.courseerpsystem.exception.AlreadyExistsException;
import org.example.courseerpsystem.exception.DataNotFoundException;
import org.example.courseerpsystem.repository.LessonRepository;
import org.example.courseerpsystem.repository.ModuleRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LessonService implements BaseService<LessonResponseDTO, LessonCreateDTO> {

    private final LessonRepository lessonRepository;

    private final ModuleRepository moduleRepository;

    private final ModelMapper modelMapper;

    @Override
    public void save(LessonCreateDTO lessonCreateDTO) {
        if (lessonRepository.findByNumeric(lessonCreateDTO.getNumeric()).isPresent()) {
            throw new AlreadyExistsException("Lesson is already exists with this numeric");
        }
        LessonEntity lessonEntity = modelMapper.map(lessonCreateDTO, LessonEntity.class);
        lessonEntity.setStatus(LessonStatus.WAIT);
        lessonEntity.setModule(moduleRepository.findById(lessonCreateDTO.getModuleId()).get());
        lessonRepository.save(lessonEntity);
    }

    @Override
    public void delete(UUID id) {
        lessonRepository.deleteById(id);
    }

    @Override
    public LessonResponseDTO update(LessonCreateDTO lessonCreateDTO, UUID id) {
        LessonEntity lessonEntity = lessonRepository.findById(id).orElseThrow(() -> new DataNotFoundException("Lesson is not found with this id"));
        lessonEntity.setTopic(lessonEntity.getTopic());
        lessonEntity.setDate(lessonCreateDTO.getDate());
        lessonEntity.setDescription(lessonCreateDTO.getDescription());
        lessonEntity.setNumeric(lessonCreateDTO.getNumeric());
        return modelMapper.map(lessonRepository.save(lessonEntity), LessonResponseDTO.class);
    }

    @Override
    public LessonResponseDTO findById(UUID id) {
        LessonEntity lessonEntity = lessonRepository.findById(id).orElseThrow(() -> new DataNotFoundException("Lesson is not found with this id"));
        return modelMapper.map(lessonEntity, LessonResponseDTO.class);
    }

    @Override
    public List<LessonResponseDTO> findAll() {
        return lessonRepository.findAll().stream()
                .map(lessonEntity -> {
                    return modelMapper.map(lessonEntity, LessonResponseDTO.class);
                }).toList();
    }

    public List<LessonResponseDTO> findAllByModuleId(UUID moduleId){
        return lessonRepository.findByModuleId(moduleId).stream()
                .map(lessonEntity -> {
                    return modelMapper.map(lessonEntity, LessonResponseDTO.class);
                }).toList();
    }
    public LessonResponseDTO changeStatus(UUID lessonId, LessonStatus status) {
        LessonEntity lessonEntity = lessonRepository.findById(lessonId).orElseThrow(() -> new DataNotFoundException("Lesson is not found with this id"));
        lessonEntity.setStatus(status);
        return modelMapper.map(lessonRepository.save(lessonEntity), LessonResponseDTO.class);
    }
}
