package com.pabloph.document_service.service.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.pabloph.document_service.dto.DocumentResponse;
import com.pabloph.document_service.dto.DocumentUploadResponse;
import com.pabloph.document_service.entity.Document;
import com.pabloph.document_service.entity.enums.DocumentStatus;
import com.pabloph.document_service.repository.DocumentRepository;
import com.pabloph.document_service.service.FileStorageService;

import org.springframework.core.io.Resource;

import com.pabloph.document_service.service.DocumentService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service 
@RequiredArgsConstructor  
public class DocumentServiceImpl implements DocumentService {

    private final DocumentRepository documentRepository;
    private final FileStorageService fileStorageService;
    
    
    @Transactional
    @Override
    public DocumentUploadResponse upload(Long userId, MultipartFile file) {

        String filePath = fileStorageService.store(file);

        try {

            Document document = Document.builder()
                    .userId(userId)
                    .name(removeExtension(file.getOriginalFilename()))
                    .originalName(file.getOriginalFilename())
                    .contentType(file.getContentType())
                    .filePath(filePath)
                    .size(file.getSize())
                    .status(DocumentStatus.UPLOADED)
                    .createdAt(LocalDateTime.now())
                    .build();

            Document savedDocument = documentRepository.save(document);

            return new DocumentUploadResponse(
                    savedDocument.getId(),
                    savedDocument.getOriginalName(),
                    savedDocument.getStatus(),
                    "Documento subido correctamente"
            );

        } catch (RuntimeException e) {

            // Si falla la BD, elimina el archivo que ya se había guardado.
            fileStorageService.delete(filePath);

            throw e;
        }
    }


    // Lista los documentos pertenecientes a un usuario.
    @Transactional()
    @Override
    public List<DocumentResponse> findAllByUser(Long userId) {

        return documentRepository.findByUserId(userId)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    // Busca un documento por su ID.
    @Override
    @Transactional()
    public DocumentResponse findById(Long id) {

        Document document = findDocument(id);

        return toResponse(document);
    }

    // Elimina el archivo físico y su registro en MySQL.
    @Override
    @Transactional
    public void delete(Long id) {

        Document document = findDocument(id);

        fileStorageService.delete(document.getFilePath());

        documentRepository.delete(document);
    }

    // Obtiene el PDF físico para descargarlo.
    @Override
    @Transactional()
    public Resource download(Long id) {

        Document document = findDocument(id);

        return fileStorageService.load(document.getFilePath());
    }

    // Busca la entidad Document.
    private Document findDocument(Long id) {

        return documentRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Documento no encontrado con ID: " + id
                        )
                );
    }

    // Convierte Document a DocumentResponse.
    private DocumentResponse toResponse(Document document) {

        return new DocumentResponse(
                document.getId(),
                document.getName(),
                document.getOriginalName(),
                document.getContentType(),
                document.getSize(),
                document.getStatus(),
                document.getCreatedAt()
        );
    }

    // Convierte "spring.pdf" en "spring".
    private String removeExtension(String fileName) {

        if (fileName == null || !fileName.contains(".")) {
            return fileName;
        }

        return fileName.substring(
                0,
                fileName.lastIndexOf(".")
        );
    }
    
        
    
}
