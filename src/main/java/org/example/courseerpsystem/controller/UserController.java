package org.example.courseerpsystem.controller;

import lombok.RequiredArgsConstructor;
import org.example.courseerpsystem.domain.dto.request.UserCreateDTO;
import org.example.courseerpsystem.domain.dto.response.UserResponseDTO;
import org.example.courseerpsystem.domain.enums.Permission;
import org.example.courseerpsystem.domain.enums.UserRole;
import org.example.courseerpsystem.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PreAuthorize("hasRole('SUPER_ADMIN')")
    @GetMapping("/findAll")
    public ResponseEntity<List<UserResponseDTO>> findAll() {
        return ResponseEntity.ok(userService.findAll());
    }

    @PreAuthorize("hasRole('SUPER_ADMIN')")
    @GetMapping("/find/{id}")
    public ResponseEntity<UserResponseDTO> findById(@PathVariable UUID id) {
        return ResponseEntity.ok(userService.findById(id));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<UserResponseDTO> update(
            @RequestBody UserCreateDTO userCreateDTO,
            @PathVariable UUID id
    ) {
        return ResponseEntity.ok(userService.update(userCreateDTO, id));
    }

    @PreAuthorize("(hasRole('ADMIN') and hasAuthority('USER_DELETE')) or hasRole('SUPER_ADMIN')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable UUID id) {
        userService.delete(id);
        return ResponseEntity.status(HttpStatus.valueOf(204)).build();
    }

    @PreAuthorize("hasRole('SUPER_ADMIN')")
    @PatchMapping("/addPermission/{id}")
    public ResponseEntity<UserResponseDTO> addPermission(
            @RequestParam Set<Permission> permissions,
            @PathVariable UUID id
    ) {
        return ResponseEntity.ok(userService.addPermission(permissions, id));
    }

    @PreAuthorize("hasRole('SUPER_ADMIN')")
    @PatchMapping("/changeUserRole/{id}")
    public ResponseEntity<UserResponseDTO> changeUserRole(
            @RequestParam UserRole role,
            @PathVariable UUID id
    ) {
        return ResponseEntity.ok(userService.changeUserRole(role, id));
    }

}
