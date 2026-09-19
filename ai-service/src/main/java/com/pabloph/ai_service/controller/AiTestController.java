package com.pabloph.ai_service.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/ai")
public class AiTestController {

    private final ChatClient chatClient;

    public AiTestController(ChatClient.Builder builder) {
        this.chatClient = builder.build();
    }

    @GetMapping("/test")
    public String test(
            @RequestParam(defaultValue = "Explica brevemente qué es Spring Boot") String message
    ) {
        return chatClient
                .prompt()
                .user(message)
                .call()
                .content();
    }
}