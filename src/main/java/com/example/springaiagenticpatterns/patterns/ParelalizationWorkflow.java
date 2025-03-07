package com.example.springaiagenticpatterns.patterns;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Component;

@Component
public class ParelalizationWorkflow {
    private final ChatClient chatClient;

    public ParelalizationWorkflow(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder.build();
    }
}
