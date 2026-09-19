package com.pabloph.ai_service.controller;

import com.pabloph.ai_service.client.DocumentServiceClient;
import com.pabloph.ai_service.dto.DocumentInternalResponse;
import com.pabloph.ai_service.service.PdfExtractionService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/ai/documents")
public class DocumentClientTestController {

    private final PdfExtractionService pdfExtractionService;
    private final DocumentServiceClient documentServiceClient;

    public DocumentClientTestController(
        DocumentServiceClient documentServiceClient,
        PdfExtractionService pdfExtractionService
) {
    this.documentServiceClient = documentServiceClient;
    this.pdfExtractionService = pdfExtractionService;
}

    @GetMapping("/{id}/test")
    public DocumentInternalResponse test(@PathVariable Long id) {
        return documentServiceClient.findById(id);
    }

    @GetMapping("/{id}/download-test")
    public String downloadTest(@PathVariable Long id) {

    byte[] pdf = documentServiceClient.download(id);

        return "PDF descargado correctamente. Tamaño: "
            + pdf.length
            + " bytes";
    }

    @GetMapping("/{id}/text-test")
    public String extractText(@PathVariable Long id) {

        DocumentInternalResponse document =
                documentServiceClient.findById(id);

        byte[] pdf =
                documentServiceClient.download(id);

        return pdfExtractionService.extractText(
                pdf,
                document.originalName()
        );
    }
}