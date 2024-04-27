package org.example.courseerpsystem.controller;

import lombok.RequiredArgsConstructor;
import org.example.courseerpsystem.domain.dto.request.GroupCreateDTO;
import org.example.courseerpsystem.domain.dto.response.GroupResponseDTO;
import org.example.courseerpsystem.service.GroupService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/group")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class GroupController {

    private final GroupService groupService;

    @PostMapping("/create")
    public ResponseEntity<String> create(@RequestBody GroupCreateDTO createDTO) {
        groupService.save(createDTO);
        return ResponseEntity.status(HttpStatus.valueOf(201)).build();
    }

    @PostMapping("/addUser/{groupId}/{userId}")
    public ResponseEntity<GroupResponseDTO> addUser(
            @PathVariable UUID groupId,
            @PathVariable UUID userId
    ){
        return ResponseEntity.ok(groupService.addUser(groupId, userId));
    }

    @GetMapping("/findAll")
    public ResponseEntity<List<GroupResponseDTO>> findAll() {
        return ResponseEntity.ok(groupService.findAll());
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<GroupResponseDTO> findById(@PathVariable UUID id) {
        return ResponseEntity.ok(groupService.findById(id));
    }

    @GetMapping("/findByCourseId/{courseId}")
    public ResponseEntity<List<GroupResponseDTO>> findByCourseId(@PathVariable UUID courseId) {
        return ResponseEntity.ok(groupService.findAllByCourseId(courseId));
    }

    @GetMapping("/findByMentorId/{mentorId}")
    @PreAuthorize("hasAnyRole('ADMIN','MENTOR')")
    public ResponseEntity<List<GroupResponseDTO>> findByMentorId(@PathVariable UUID mentorId) {
        return ResponseEntity.ok(groupService.findAllByMentorId(mentorId));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<GroupResponseDTO> update(
            @RequestBody GroupCreateDTO createDTO,
            @PathVariable UUID id) {
        return ResponseEntity.ok(groupService.update(createDTO, id));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable UUID id) {
        groupService.delete(id);
        return ResponseEntity.status(HttpStatus.valueOf(204)).build();
    }

}
