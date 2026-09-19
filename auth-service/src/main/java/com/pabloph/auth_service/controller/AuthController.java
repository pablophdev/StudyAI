package com.pabloph.auth_service.controller;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.core.Authentication;
import com.pabloph.auth_service.dto.AuthResponse;
import com.pabloph.auth_service.dto.LoginRequest;
import com.pabloph.auth_service.dto.RegisterRequest;
import com.pabloph.auth_service.service.AuthService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    
    @PostMapping("/register")
    public ResponseEntity<Map<String, String>> register(
            @Valid @RequestBody RegisterRequest request
    ) {

        authService.register(request);

        return ResponseEntity.ok(
                Map.of("message", "Usuario registrado correctamente")
        );
    }
    

    @PostMapping("/login")
        public ResponseEntity<AuthResponse> login(
        @Valid @RequestBody LoginRequest request
        ) {

        return ResponseEntity.ok(
                    authService.login(request)
        );


        
        }


        @GetMapping("/me")
        public ResponseEntity<Map<String, String>> me(Authentication authentication) {

        return ResponseEntity.ok(
                    Map.of(
                            "email", authentication.getName(),
                        "message", "Token válido"
                )
        );
        }
}
