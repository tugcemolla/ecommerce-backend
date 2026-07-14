package com.tugce.ecommerce.service.impl;

import com.tugce.ecommerce.dto.AuthResponseDTO;
import com.tugce.ecommerce.dto.LoginRequestDTO;
import com.tugce.ecommerce.entity.User;
import com.tugce.ecommerce.repository.UserRepository;
import com.tugce.ecommerce.service.AuthService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.tugce.ecommerce.security.JwtService;

@Service
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthServiceImpl(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    @Override
    public AuthResponseDTO login(LoginRequestDTO loginRequestDTO) {
        User user = userRepository.findByEmail(loginRequestDTO.getEmail())
                .orElseThrow(() -> new RuntimeException("E-posta veya şifre hatalı."));

        boolean passwordMatches = passwordEncoder.matches(
                loginRequestDTO.getPassword(),
                user.getPassword()
        );

        if (!passwordMatches) {
            throw new RuntimeException("E-posta veya şifre hatalı");
        }
        String token = jwtService.generateToken(user);

        return AuthResponseDTO.builder()
                .token(token)
                .tokenType("Bearer")
                .userId(user.getId())
                .email(user.getEmail())
                .role(user.getRole())
                .build();
    }
}
