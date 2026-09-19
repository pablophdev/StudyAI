package com.pabloph.ai_service.controller;

import java.util.List;

import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/ai/vector")
public class VectorTestController {

    private final VectorStore vectorStore;

    public VectorTestController(VectorStore vectorStore) {
        this.vectorStore = vectorStore;
    }

    @PostMapping("/test")
    public String saveTestEmbedding() {

        Document document = Document.builder()
                .text("Spring Boot permite desarrollar aplicaciones backend en Java de forma rápida y estructurada.")
                .metadata("source", "manual-test")
                .metadata("userId", 1)
                .metadata("documentId", 1)
                .build();

        vectorStore.add(List.of(document));

        return "Embedding guardado correctamente en PGVector";
    }

    @GetMapping("/search")
    public List<String> search(@RequestParam String query) {

        return vectorStore.similaritySearch(
                SearchRequest.builder()
                        .query(query)
                        .topK(3)
                        .build()
        )
        .stream()
        .map(Document::getText)
        .toList();
}
}