package org.example.courseerpsystem.service;

import lombok.RequiredArgsConstructor;
import org.example.courseerpsystem.domain.dto.request.LessonContentCreateDTO;
import org.example.courseerpsystem.domain.dto.response.LessonContentResponseDTO;
import org.example.courseerpsystem.domain.entity.LessonContentEntity;
import org.example.courseerpsystem.exception.DataNotFoundException;
import org.example.courseerpsystem.repository.LessonContentRepository;
import org.example.courseerpsystem.repository.LessonRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LessonContentService implements BaseService<LessonContentResponseDTO, LessonContentCreateDTO> {

    private final LessonContentRepository contentRepository;

    private final LessonRepository lessonRepository;

    private final ModelMapper modelMapper;

    @Override
    public void save(LessonContentCreateDTO lessonContentCreateDTO) {
        LessonContentEntity contentEntity = modelMapper.map(lessonContentCreateDTO, LessonContentEntity.class);
        contentEntity.setLesson(lessonRepository.findById(lessonContentCreateDTO.getLessonId()).get());
        contentRepository.save(contentEntity);
    }

    @Override
    public void delete(UUID id) {
        contentRepository.deleteById(id);
    }

    @Override
    public LessonContentResponseDTO update(LessonContentCreateDTO lessonContentCreateDTO, UUID id) {
        LessonContentEntity lessonContentEntity = contentRepository.findById(id).orElseThrow(() -> new DataNotFoundException("Content is not found with this id"));
        lessonContentEntity.setDescription(lessonContentCreateDTO.getDescription());
        return modelMapper.map(lessonContentEntity, LessonContentResponseDTO.class);
    }

    @Override
    public LessonContentResponseDTO findById(UUID id) {
        LessonContentEntity lessonContentEntity = contentRepository.findById(id).orElseThrow(() -> new DataNotFoundException("Content is not found with this id"));
        return modelMapper.map(lessonContentEntity, LessonContentResponseDTO.class);
    }

    @Override
    public List<LessonContentResponseDTO> findAll() {
        return contentRepository.findAll().stream()
                .map(lessonContentEntity -> {
                    return modelMapper.map(lessonContentEntity, LessonContentResponseDTO.class);
                }).toList();
    }

    public List<LessonContentResponseDTO> findAllByLessonId(UUID lessonId) {
        return contentRepository.findByLessonId(lessonId).stream()
                .map(lessonContentEntity -> {
                    return modelMapper.map(lessonContentEntity, LessonContentResponseDTO.class);
                }).toList();
    }
}
