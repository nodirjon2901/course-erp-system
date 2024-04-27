package org.example.courseerpsystem.controller;

import lombok.RequiredArgsConstructor;
import org.example.courseerpsystem.domain.dto.request.LessonContentCreateDTO;
import org.example.courseerpsystem.domain.dto.response.LessonContentResponseDTO;
import org.example.courseerpsystem.service.LessonContentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/content")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class LessonContentController {

    private final LessonContentService contentService;

    @PostMapping("/create")
    public ResponseEntity<String> create(@RequestBody LessonContentCreateDTO createDTO) {
        contentService.save(createDTO);
        return ResponseEntity.status(HttpStatus.valueOf(201)).build();
    }

    @PreAuthorize("hasAnyRole('ADMIN','MENTOR')")
    @GetMapping("/findAll")
    public ResponseEntity<List<LessonContentResponseDTO>> findAll() {
        return ResponseEntity.ok(contentService.findAll());
    }

    @PreAuthorize("hasAnyRole('ADMIN','MENTOR','STUDENT')")
    @GetMapping("/findAll/{lessonId}")
    public ResponseEntity<List<LessonContentResponseDTO>> findAllByLessonId(@PathVariable UUID lessonId) {
        return ResponseEntity.ok(contentService.findAllByLessonId(lessonId));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable UUID id) {
        contentService.delete(id);
        return ResponseEntity.status(HttpStatus.valueOf(204)).build();
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<LessonContentResponseDTO> update(
            @RequestBody LessonContentCreateDTO createDTO,
            @PathVariable UUID id
    ) {
        return ResponseEntity.ok(contentService.update(createDTO, id));
    }

}
