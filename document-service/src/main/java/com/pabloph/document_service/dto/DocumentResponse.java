package com.pabloph.document_service.dto;

import java.time.LocalDateTime;
import com.pabloph.document_service.entity.enums.DocumentStatus;

public record DocumentResponse(
    Long id,
    String name,
    String originalName,
    String contentType,
    Long size,
    DocumentStatus status,
    LocalDateTime createdAt
) {}