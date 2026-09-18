package com.pabloph.document_service.dto;

import com.pabloph.document_service.entity.enums.DocumentStatus;

public record DocumentResponse(
    Long id,
    String name,
    String originalName,
    String contentType,
    String filePath,
    DocumentStatus status
) {
}
