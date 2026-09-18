package com.pabloph.document_service.dto;

import com.pabloph.document_service.entity.enums.DocumentStatus;

public record DocumentUploadResponse(
        Long id,
        String originalName,
        DocumentStatus status,
        String message
) {}