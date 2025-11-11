package com.VirtualBookStore.security;

import com.VirtualBookStore.dao.UserRepository;
import com.VirtualBookStore.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final AuthUtil authUtil;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    // Login using email now
    public LoginResponseDto login(LoginRequestDto loginRequestDto) {
        // Authenticate credentials (email and password)
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequestDto.getEmail(),
                        loginRequestDto.getPassword()
                )
        );

        // Fetch user entity after successful authentication
        User user = userRepository.findByEmail(loginRequestDto.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Generate JWT token embedding user info & role
        String token = authUtil.generateAccessToken(user);

        // Return token and user id in response DTO
        return new LoginResponseDto(token, user.getId());
    }

    // Signup method
    public SignupResponseDto signup(SignUpRequestDto signupRequestDto) {
        if (userRepository.findByEmail(signupRequestDto.getEmail()).isPresent()) {
            throw new RuntimeException("User already exists");
        }

        User user = User.builder()
                .name(signupRequestDto.getName())
                .email(signupRequestDto.getEmail())
                .password(passwordEncoder.encode(signupRequestDto.getPassword()))
                .role(RoleType.USER)
                .build();


        User savedUser = userRepository.save(user);

        return new SignupResponseDto(savedUser.getId(), savedUser.getName());
    }
}
