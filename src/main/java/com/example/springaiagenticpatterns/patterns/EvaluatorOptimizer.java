package com.example.springaiagenticpatterns.patterns;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Component;

@Component
public class EvaluatorOptimizer {
    private final ChatClient chatClient;

    public EvaluatorOptimizer(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder.build();
    }
}
