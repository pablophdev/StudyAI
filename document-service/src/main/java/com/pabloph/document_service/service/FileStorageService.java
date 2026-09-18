package com.pabloph.document_service.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface FileStorageService {

    String store(MultipartFile file);

    Resource load(String filePath);

    void delete(String filePath);
}