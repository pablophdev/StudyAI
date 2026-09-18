package com.pabloph.document_service.controller;

import java.util.List;

import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.pabloph.document_service.dto.DocumentResponse;
import com.pabloph.document_service.dto.DocumentUploadResponse;
import com.pabloph.document_service.service.DocumentService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/documents")
@RequiredArgsConstructor
public class DocumentController {

    private final DocumentService documentService;

    // Sube un PDF.
    @PostMapping("/upload")
    public ResponseEntity<DocumentUploadResponse> upload(
            @RequestParam Long userId,
            @RequestParam("file") MultipartFile file
    ) {

        return ResponseEntity.ok(
                documentService.upload(userId, file)
        );
    }

    // Lista los documentos de un usuario.
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<DocumentResponse>> findAllByUser(
            @PathVariable Long userId
    ) {

        return ResponseEntity.ok(
                documentService.findAllByUser(userId)
        );
    }

    // Busca un documento por ID.
    @GetMapping("/{id}")
    public ResponseEntity<DocumentResponse> findById(
            @PathVariable Long id
    ) {

        return ResponseEntity.ok(
                documentService.findById(id)
        );
    }

    // Descarga el archivo.
    @GetMapping("/{id}/download")
    public ResponseEntity<Resource> download(
            @PathVariable Long id
    ) {

        Resource resource = documentService.download(id);

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_PDF)
                .header(
                        HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + resource.getFilename() + "\""
                )
                .body(resource);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable Long id
    ) {

        documentService.delete(id);

        return ResponseEntity.noContent().build();
    }
}

