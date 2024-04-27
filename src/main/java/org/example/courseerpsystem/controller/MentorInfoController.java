package org.example.courseerpsystem.controller;

import lombok.RequiredArgsConstructor;
import org.example.courseerpsystem.domain.dto.request.MentorInfoCreateDTO;
import org.example.courseerpsystem.domain.dto.response.MentorInfoResponseDTO;
import org.example.courseerpsystem.service.MentorInfoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/mentorInfo")
@RequiredArgsConstructor
public class MentorInfoController {

    private final MentorInfoService mentorInfoService;

    @PreAuthorize("hasRole('MENTOR')")
    @PostMapping("/create")
    public ResponseEntity<String> create(@RequestBody MentorInfoCreateDTO createDTO) {
        mentorInfoService.save(createDTO);
        return ResponseEntity.status(HttpStatus.valueOf(201)).build();
    }

    @PreAuthorize("hasAnyRole('ADMIN','MENTOR')")// doesn't work
    @GetMapping("/findAll")
    public ResponseEntity<List<MentorInfoResponseDTO>> findAll() {
        return ResponseEntity.ok(mentorInfoService.findAll());
    }

    @PreAuthorize("hasRole('MENTOR')")
    @GetMapping("/find/{id}")
    public ResponseEntity<MentorInfoResponseDTO> findById(@PathVariable UUID id) {
        return ResponseEntity.ok(mentorInfoService.findById(id));
    }

    @PreAuthorize("hasRole('MENTOR')")
    @GetMapping("/findByUserId/{userId}")
    public ResponseEntity<MentorInfoResponseDTO> findByUserId(@PathVariable UUID userId){
        return ResponseEntity.ok(mentorInfoService.findByUserId(userId));
    }

    @PreAuthorize("hasRole('MENTOR')")
    @PutMapping("/update/{id}")
    public ResponseEntity<MentorInfoResponseDTO> update(
            @RequestBody MentorInfoCreateDTO createDTO,
            @PathVariable UUID id
    ) {
        return ResponseEntity.ok(mentorInfoService.update(createDTO, id));
    }

    @PreAuthorize("hasRole('MENTOR')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable UUID id) {
        mentorInfoService.delete(id);
        return ResponseEntity.status(HttpStatus.valueOf(204)).build();
    }

}
