package org.example.courseerpsystem.controller;

import lombok.RequiredArgsConstructor;
import org.example.courseerpsystem.domain.dto.request.CourseCreateDTO;
import org.example.courseerpsystem.domain.dto.response.CourseResponseDTO;
import org.example.courseerpsystem.service.CourseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/course")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class CourseController {

    private final CourseService courseService;

    @PostMapping("/create")
    public ResponseEntity<String> create(@RequestBody CourseCreateDTO createDTO) {
        courseService.save(createDTO);
        return ResponseEntity.status(HttpStatus.valueOf(201)).build();
    }

    @GetMapping("/findAll")
    public ResponseEntity<List<CourseResponseDTO>> findAll() {
        return ResponseEntity.ok(courseService.findAll());
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<CourseResponseDTO> findById(@PathVariable UUID id) {
        return ResponseEntity.ok(courseService.findById(id));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<CourseResponseDTO> update(
            @RequestBody CourseCreateDTO createDTO,
            @PathVariable UUID id) {
        return ResponseEntity.ok(courseService.update(createDTO, id));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable UUID id) {
        courseService.delete(id);
        return ResponseEntity.status(HttpStatus.valueOf(204)).build();
    }

}
