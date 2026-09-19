package com.pabloph.auth_service.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegisterRequest(
    
        @NotBlank(message = "El username es obligatorio")
        @Size(min = 3, max = 30, message = "El username debe tener entre 3 y 30 caracteres")
        String username,

        @NotBlank(message = "El email es obligatorio")
        @Email(message = "El email no es válido")
        @Size(max = 120)
        String email,

        @NotBlank(message = "La contraseña es obligatoria")
        @Size(min = 8, message = "La contraseña debe tener mínimo 8 caracteres")
        String password
) {
}
