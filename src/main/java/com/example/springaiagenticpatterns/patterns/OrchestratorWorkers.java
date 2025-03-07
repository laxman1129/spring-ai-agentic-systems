package com.example.springaiagenticpatterns.patterns;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Component;

@Component
public class OrchestratorWorkers {
    private final ChatClient chatClient;

    public OrchestratorWorkers(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder.build();
    }
}
