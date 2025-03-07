package com.example.springaiagenticpatterns.patterns;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

/**
 * Processing large volumes of similar but independent items
 */
@Component
public class ParelalizationWorkflow {
    private final ChatClient chatClient;

    public ParelalizationWorkflow(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder.build();
    }

    public List<String> parallel(String prompt, List<String> inputs, int nWorkers) {
        Assert.notNull(prompt, "Prompt cannot be null");
        Assert.notEmpty(inputs, "Inputs list cannot be empty");
        Assert.isTrue(nWorkers > 0, "Number of workers must be greater than 0");

        try (ExecutorService executor = Executors.newFixedThreadPool(nWorkers)) {
            List<CompletableFuture<String>> futures = inputs.stream()
                    .map(input -> CompletableFuture.supplyAsync(() -> {
                        try {
                            return chatClient.prompt(prompt + "\nInput: " + input).call().content();
                        } catch (Exception e) {
                            throw new RuntimeException("Failed to process input: " + input, e);
                        }
                    }, executor))
                    .toList();

            // Wait for all tasks to complete
            CompletableFuture<Void> allFutures = CompletableFuture.allOf(
                    futures.toArray(CompletableFuture[]::new));
            allFutures.join();

            List<String> response = futures.stream()
                    .map(CompletableFuture::join)
                    .collect(Collectors.toList());
            System.out.printf("Responses: %s%n", response);
            System.out.println("""
                    ========>
                    ========>
                    ========>
                    ========>
                    """);
            var summary = chatClient.prompt("""
                    summarize the response in 50 words
                    response :
                    """ + String.join(",", response)).call().content();
            System.out.printf("Responses: %s%n", summary);


            return response;
        }

    }
}
