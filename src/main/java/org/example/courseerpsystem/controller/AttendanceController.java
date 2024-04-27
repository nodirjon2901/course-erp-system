package org.example.courseerpsystem.controller;

import lombok.RequiredArgsConstructor;
import org.example.courseerpsystem.domain.dto.request.AttendanceCreateDTO;
import org.example.courseerpsystem.domain.dto.response.AttendanceResponseDTO;
import org.example.courseerpsystem.service.AttendanceService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/attendance")
@RequiredArgsConstructor
public class AttendanceController {

    private final AttendanceService attendanceService;

    @PostMapping("/create")
    public ResponseEntity<String> create(@RequestBody AttendanceCreateDTO createDTO) {
        attendanceService.save(createDTO);
        return ResponseEntity.status(HttpStatus.valueOf(201)).build();
    }

    @GetMapping("/findAll")
    public ResponseEntity<List<AttendanceResponseDTO>> findAll() {
        return ResponseEntity.ok(attendanceService.findAll());
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<AttendanceResponseDTO> findById(@PathVariable UUID id) {
        return ResponseEntity.ok(attendanceService.findById(id));
    }

    @GetMapping("/findAll/{userId}")
    public ResponseEntity<List<AttendanceResponseDTO>> findAllByUserId(@PathVariable UUID userId) {
        return ResponseEntity.ok(attendanceService.findAllByUserId(userId));
    }

    @GetMapping("/findAll/{lessonId}")
    public ResponseEntity<List<AttendanceResponseDTO>> findAllByLessonId(@PathVariable UUID lessonId) {
        return ResponseEntity.ok(attendanceService.findAllByLessonId(lessonId));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<AttendanceResponseDTO> update(
            @RequestBody AttendanceCreateDTO createDTO,
            @PathVariable UUID id) {
        return ResponseEntity.ok(attendanceService.update(createDTO, id));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable UUID id) {
        attendanceService.delete(id);
        return ResponseEntity.status(HttpStatus.valueOf(204)).build();
    }

}
