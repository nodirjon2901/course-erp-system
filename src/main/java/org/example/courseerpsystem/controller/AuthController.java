package org.example.courseerpsystem.controller;

import lombok.RequiredArgsConstructor;
import org.example.courseerpsystem.domain.dto.request.UserCreateDTO;
import org.example.courseerpsystem.domain.dto.request.UserLoginDTO;
import org.example.courseerpsystem.domain.dto.response.JwtResponseDTO;
import org.example.courseerpsystem.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    @PostMapping("/sign-up")
    public ResponseEntity<String> signUp(@RequestBody UserCreateDTO userCreateDTO) {
        userService.save(userCreateDTO);
        return ResponseEntity.status(HttpStatus.valueOf(201)).build();
    }

    @PostMapping("/sign-in")
    public ResponseEntity<JwtResponseDTO> signIn(@RequestBody UserLoginDTO loginDTO) {
        return ResponseEntity.ok(userService.login(loginDTO));
    }


}
