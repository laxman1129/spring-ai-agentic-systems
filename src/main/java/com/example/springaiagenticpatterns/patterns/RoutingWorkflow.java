package com.example.springaiagenticpatterns.patterns;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Component;

@Component
public class RoutingWorkflow {
    private final ChatClient chatClient;

    public RoutingWorkflow(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder.build();
    }
}
