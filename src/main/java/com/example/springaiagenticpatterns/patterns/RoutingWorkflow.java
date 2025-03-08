package com.example.springaiagenticpatterns.patterns;

import com.example.springaiagenticpatterns.records.RoutingResponse;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Map;

/**
 * Routing Workflow: for specialized processing, different types of inputs are routed to specialized processing steps.
 */
@Component
public class RoutingWorkflow {
    private final ChatClient chatClient;

    public RoutingWorkflow(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder.build();
    }

    public void route(Map<String, String> supportRoutes, List<String> tickets) {
        int i = 1;
        for (String ticket : tickets) {
            System.out.println("\nTicket " + i++);
            System.out.println("------------------------------------------------------------");
            System.out.println(ticket);
            System.out.println("------------------------------------------------------------");
            System.out.println(this.execute(ticket, supportRoutes));
        }
    }

    private String execute(String ticket, Map<String, String> routes) {
        Assert.notNull(ticket, "Input text cannot be null");
        Assert.notEmpty(routes, "Routes map cannot be null or empty");

        // Determine the appropriate route for the input
        String routeKey = determineRoute(ticket, routes.keySet());
        System.out.printf("Selected route: %s%n", routeKey);

        // Get the selected prompt from the routes map
        String selectedPrompt = routes.get(routeKey);

        if (selectedPrompt == null) {
            throw new IllegalArgumentException("Selected route '" + routeKey + "' not found in routes map");
        }

        // Process the input with the selected prompt
        return chatClient.prompt(selectedPrompt + "\nInput: " + ticket).call().content();
    }

    @SuppressWarnings("null")
    private String determineRoute(String input, Iterable<String> availableRoutes) {
        System.out.println("\nAvailable routes: " + availableRoutes);

        String selectorPrompt = String.format("""
                Analyze the input and select the most appropriate support team from these options: %s
                First explain your reasoning, then provide your selection in this JSON format:
                
                \\{
                    "reasoning": "Brief explanation of why this ticket should be routed to a specific team.
                                Consider key terms, user intent, and urgency level.",
                    "selection": "The chosen team name"
                \\}
                
                Input: %s""", availableRoutes, input);

        RoutingResponse routingResponse = chatClient.prompt(selectorPrompt).call().entity(RoutingResponse.class);

        assert routingResponse != null;
        System.out.printf("Routing Analysis:%s\nSelected route: %s%n",
                routingResponse.reasoning(), routingResponse.selection());

        return routingResponse.selection();
    }
}
