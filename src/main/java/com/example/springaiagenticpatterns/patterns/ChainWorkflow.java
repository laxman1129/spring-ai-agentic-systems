package com.example.springaiagenticpatterns.patterns;

import com.example.springaiagenticpatterns.args.ChainWorkflowArgs;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Component;

/**
 * 4 step workflow
 * 1. Extract numerical values and metrics
 * 2.Standardize to percentage format
 * 3. Sort in descending order
 * 4. Format as markdown table
 */
@Component
public class ChainWorkflow {
    private final ChatClient chatClient;

    public ChainWorkflow(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder.build();
    }

    public String chain(String userInput) {
        var systemPrompts = ChainWorkflowArgs.DEFAULT_SYSTEM_PROMPTS;
        int step = 0;
        String response = userInput;
        System.out.printf("\nSTEP %s:\n %s%n", step++, response);

        for (String prompt : systemPrompts) {

            // 1. Compose the input using the response from the previous step.
            String input = String.format("{%s}\n {%s}", prompt, response);

            // 2. Call the chat client with the new input and get the new response.
            response = chatClient.prompt(input).call().content();

            System.out.printf("\nSTEP %s:\n %s%n", step++, response);
        }

        return response;
    }
}
