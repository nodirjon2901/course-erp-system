package org.example.courseerpsystem.controller;

import lombok.RequiredArgsConstructor;
import org.example.courseerpsystem.domain.dto.request.ModuleCreateDTO;
import org.example.courseerpsystem.domain.dto.response.ModuleResponseDTO;
import org.example.courseerpsystem.service.ModuleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/module")
@RequiredArgsConstructor
@PreAuthorize("hasRole('MENTOR')")
public class ModuleController {

    private final ModuleService moduleService;

    @PostMapping("/create")
    public ResponseEntity<String> create(@RequestBody ModuleCreateDTO createDTO) {
        moduleService.save(createDTO);
        return ResponseEntity.status(HttpStatus.valueOf(201)).build();
    }

    @PostMapping("/addGroup/{moduleId}/{groupId}")
    public ResponseEntity<ModuleResponseDTO> addGroup(
            @PathVariable UUID moduleId,
            @PathVariable UUID groupId
    ){
        return ResponseEntity.ok(moduleService.addGroup(moduleId, groupId));
    }

    @GetMapping("/findAll")
    public ResponseEntity<List<ModuleResponseDTO>> findAll() {
        return ResponseEntity.ok(moduleService.findAll());
    }

    @GetMapping("/findAll/{courseId}")
    public ResponseEntity<List<ModuleResponseDTO>> findAllByCourseId(@PathVariable UUID courseId) {
        return ResponseEntity.ok(moduleService.findAllByCourseId(courseId));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable UUID id) {
        moduleService.delete(id);
        return ResponseEntity.status(HttpStatus.valueOf(204)).build();
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ModuleResponseDTO> update(
            @RequestBody ModuleCreateDTO createDTO,
            @PathVariable UUID id) {
        return ResponseEntity.ok(moduleService.update(createDTO, id));
    }

}
