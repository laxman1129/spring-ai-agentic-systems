package com.example.springaiagenticpatterns;

import com.example.springaiagenticpatterns.patterns.ChainWorkflow;
import com.example.springaiagenticpatterns.prompts.ChainWorkflowPrompts;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Scanner;

@SpringBootApplication
@RequiredArgsConstructor
public class Application implements CommandLineRunner {
    private final ChainWorkflow chainWorkflow;


    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("""
                1. Chain Workflow
                2. Parallel Workflow
                3. Routing Workflow
                4. Orchestrator-Workers Pattern
                5. Evaluator-Optimizer Pattern
                0. Exit
                """);
        Scanner input = new Scanner(System.in);
        while (true) {
            System.out.println("Enter a number (1-5) to select an agentic pattern or 0 to exit:");
            int choice = input.nextInt();
            switch (choice) {
                case 0 -> {
                    System.out.println("Exiting...");
                    System.exit(0);
                }
                case 1 -> {
                    System.out.println("Chain Workflow: for sequential processing, output of one step is input to next step.");
                    chainWorkflow.chain(ChainWorkflowPrompts.USER_INPUT);
                }
                case 2 ->
                        System.out.println("Parallel Workflow: for parallel processing, output of multiple steps are aggregated to produce final output.");
                case 3 ->
                        System.out.println("Routing Workflow: for specialized processing, different types of inputs are routed to specialized processing steps.");
                case 4 ->
                        System.out.println("Orchestrator-Workers Pattern: complex agent-like behavior while maintaining control over the process, complex tasks where subtasks can't be predicted.");
                case 5 ->
                        System.out.println("Evaluator-Optimizer Pattern: dual LLM process where one LLM model generates a solution and another LLM model evaluates the solution.");
                default -> System.out.println("Invalid input");
            }
        }
    }
}

