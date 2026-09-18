package com.pabloph.document_service.service;

import java.util.List;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import com.pabloph.document_service.dto.DocumentResponse;
import com.pabloph.document_service.dto.DocumentUploadResponse;

public interface DocumentService {

    DocumentUploadResponse upload(Long userId, MultipartFile file);

    List<DocumentResponse> findAllByUser(Long userId);

    DocumentResponse findById(Long id);

    void delete(Long id);

    Resource download(Long id);
}