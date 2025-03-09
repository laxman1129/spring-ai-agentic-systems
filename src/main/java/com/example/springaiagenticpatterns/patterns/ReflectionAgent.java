package com.example.springaiagenticpatterns.patterns;

import com.example.springaiagenticpatterns.args.ReflectionAgentArgs;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.InMemoryChatMemory;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.stereotype.Component;

@Component
public class ReflectionAgent {
    private final ChatClient generateChatClient;
    private final ChatClient critiqueChatClient;

    public ReflectionAgent(ChatModel chatModel) {
        this.generateChatClient = ChatClient.builder(chatModel)
                .defaultSystem(ReflectionAgentArgs.DEFAULT_GENERATOR_PROMPT)
                .defaultAdvisors(new MessageChatMemoryAdvisor(new InMemoryChatMemory()))
                .build();

        this.critiqueChatClient = ChatClient.builder(chatModel)
                .defaultSystem(ReflectionAgentArgs.DEFAULT_CRITIQUE_PROMPT)
                .defaultAdvisors(new MessageChatMemoryAdvisor(new InMemoryChatMemory()))
                .build();
    }

    public void reflect(String userInput, int maxIterations) {
        String generation = generateChatClient.prompt(userInput).call().content();
        System.out.println("\n\n##generation\n\n" + generation);
        String critique;
        for (int i = 0; i < maxIterations; i++) {

            assert generation != null;
            critique = critiqueChatClient.prompt(generation).call().content();

            System.out.println("\n\n##Critique\n\n" + critique);
            assert critique != null;
            if (critique.contains("<OK>")) {
                System.out.println("\n\nStop sequence found\n\n");
                break;
            }
            generation = generateChatClient.prompt(critique).call().content();
            System.out.println("\n\n##generation\n\n" + generation);
        }
        System.out.printf("\n\nFinal generation:\n\n %s%n", generation);
    }
}
