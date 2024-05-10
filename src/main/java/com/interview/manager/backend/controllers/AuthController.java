package com.interview.manager.backend.controllers;

import com.interview.manager.backend.dto.UserLoginRequestDto;
import com.interview.manager.backend.dto.UserLoginResponseDto;
import com.interview.manager.backend.dto.UserRegisterRequestDto;
import com.interview.manager.backend.dto.UserResponseDto;
import com.interview.manager.backend.services.auth.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Validated
public class AuthController {

    private final AuthService authService;


    // TODO: test everything, not tested
    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody UserLoginRequestDto requestDto) {
        UserLoginResponseDto responseDto = authService.login(requestDto);

        if (responseDto != null) {
            return ResponseEntity.ok(responseDto);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Wrong email or password");
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody UserRegisterRequestDto requestDto){
        UserResponseDto userResponseDto = authService.register(requestDto);
        if(userResponseDto != null) {
            return ResponseEntity.ok(userResponseDto);
        } else{
            return ResponseEntity.status(HttpStatus.CONFLICT).body("User already exist by this email");
        }
    }

    // TODO: renew token
}
