package com.VirtualBookStore.controller;

import com.VirtualBookStore.dto.LoginRequestDto;
import com.VirtualBookStore.dto.LoginResponseDto;
import com.VirtualBookStore.dto.SignUpRequestDto;
import com.VirtualBookStore.dto.SignupResponseDto;
import com.VirtualBookStore.security.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    // user login
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequestDto loginRequestDto) {
        LoginResponseDto response = authService.login(loginRequestDto);
        return ResponseEntity.ok(response);
    }
    // new user Registration
    @PostMapping("/signup")
    public ResponseEntity<SignupResponseDto> signup(@RequestBody SignUpRequestDto signupRequestDto) {
        SignupResponseDto response = authService.signup(signupRequestDto);
        return ResponseEntity.ok(response);
    }
}