package com.pabloph.ai_service.service;

import java.util.List;

import org.springframework.ai.document.Document;
import org.springframework.ai.reader.pdf.PagePdfDocumentReader;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Service;

@Service
public class PdfExtractionService {

    public String extractText(byte[] pdfBytes, String fileName) {

        ByteArrayResource resource = new ByteArrayResource(pdfBytes) {
            @Override
            public String getFilename() {
                return fileName;
            }
        };

        PagePdfDocumentReader reader =
                new PagePdfDocumentReader(resource);

        List<Document> documents = reader.read();

        return documents.stream()
                .map(Document::getText)
                .reduce("", (text1, text2) -> text1 + "\n" + text2);
    }
}