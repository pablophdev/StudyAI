package com.pabloph.document_service.dto;

import com.pabloph.document_service.entity.enums.DocumentStatus;


public record DocumentInternalService(
    Long id,
    Long userId,
    String originalName,
    String contentType,
    String filePath,
    DocumentStatus status
    
) {
}
