package org.example.courseerpsystem.controller;

import lombok.RequiredArgsConstructor;
import org.example.courseerpsystem.domain.dto.request.LessonCreateDTO;
import org.example.courseerpsystem.domain.dto.response.LessonResponseDTO;
import org.example.courseerpsystem.domain.enums.LessonStatus;
import org.example.courseerpsystem.service.LessonService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/lesson")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class LessonController {

    private final LessonService lessonService;

    @PostMapping("/create")
    public ResponseEntity<String> create(@RequestBody LessonCreateDTO createDTO) {
        lessonService.save(createDTO);
        return ResponseEntity.status(HttpStatus.valueOf(201)).build();
    }

    @PreAuthorize("hasAnyRole('ADMIN','MENTOR')")
    @GetMapping("/findAll")
    public ResponseEntity<List<LessonResponseDTO>> findAll() {
        return ResponseEntity.ok(lessonService.findAll());
    }

    @PreAuthorize("hasRole('MENTOR')")
    @GetMapping("/findAll/{moduleId}")
    public ResponseEntity<List<LessonResponseDTO>> findAllByModuleId(@PathVariable UUID moduleId) {
        return ResponseEntity.ok(lessonService.findAllByModuleId(moduleId));
    }

    @PreAuthorize("hasAnyRole('ADMIN','MENTOR','STUDENT')")
    @GetMapping("/find/{id}")
    public ResponseEntity<LessonResponseDTO> findById(@PathVariable UUID id) {
        return ResponseEntity.ok(lessonService.findById(id));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<LessonResponseDTO> upadate(
            @RequestBody LessonCreateDTO createDTO,
            @PathVariable UUID id
    ) {
        return ResponseEntity.ok(lessonService.update(createDTO, id));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable UUID id) {
        lessonService.delete(id);
        return ResponseEntity.status(HttpStatus.valueOf(204)).build();
    }

    @PreAuthorize("hasRole('MENTOR')")
    @PatchMapping("/changeStatus/{lessonId}")
    public ResponseEntity<LessonResponseDTO> changeStatus(
            @PathVariable UUID lessonId,
            @RequestParam LessonStatus status
    ) {
        return ResponseEntity.ok(lessonService.changeStatus(lessonId, status));
    }
}
