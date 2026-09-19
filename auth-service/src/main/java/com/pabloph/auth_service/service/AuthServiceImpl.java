package com.pabloph.auth_service.service;

import java.time.LocalDateTime;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.pabloph.auth_service.dto.AuthResponse;
import com.pabloph.auth_service.dto.LoginRequest;
import com.pabloph.auth_service.dto.RegisterRequest;
import com.pabloph.auth_service.entity.Role;
import com.pabloph.auth_service.entity.User;
import com.pabloph.auth_service.repository.UserRepository;
import com.pabloph.auth_service.security.JwtService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor 
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;


    @Override
    public void register(RegisterRequest registerRequest) {

        if (userRepository.existsByUsername(registerRequest.username())) {
            throw new RuntimeException("El username ya está registrado");
        }

        if (userRepository.existsByEmail(registerRequest.email())) {
            throw new RuntimeException("El email ya está registrado");
        }


        User user = User.builder()
                .username(registerRequest.username())
                .email(registerRequest.email())
                .password(passwordEncoder.encode(registerRequest.password()))
                .role(Role.USER)
                .createdAt(LocalDateTime.now())
                .build();

        userRepository.save(user);
    }

    
    @Override
    public AuthResponse login(LoginRequest request) {

        User user = userRepository.findByEmail(request.email())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        boolean passwordMatches = passwordEncoder.matches(
                request.password(),
                user.getPassword()
        );

        if (!passwordMatches) {
            throw new RuntimeException("Contraseña incorrecta");
        }

        //Devuelve el token
        String token = jwtService.generateToken(user);

        return new AuthResponse(
            token,
            "Bearer",
            user.getId(),
            user.getUsername(),
            user.getEmail(),
            user.getRole().name()
        );
    }
}
