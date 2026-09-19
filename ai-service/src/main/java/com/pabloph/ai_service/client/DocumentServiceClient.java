package com.pabloph.ai_service.client;

import com.pabloph.ai_service.dto.DocumentInternalResponse;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

@HttpExchange("/api/documents")
public interface DocumentServiceClient {

    @GetExchange("/{id}")
    DocumentInternalResponse findById(@PathVariable Long id);
}