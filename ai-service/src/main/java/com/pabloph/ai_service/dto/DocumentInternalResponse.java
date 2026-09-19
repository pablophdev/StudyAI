package com.pabloph.ai_service.dto;

import java.time.LocalDateTime;

public record DocumentInternalResponse(
        Long id,
        String name,
        String originalName,
        String contentType,
        Long size,
        String status,
        LocalDateTime createdAt
) {
}
