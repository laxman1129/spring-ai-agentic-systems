package com.example.springaiagenticpatterns;

import com.example.springaiagenticpatterns.args.RoutingWorkflowArgs;
import com.example.springaiagenticpatterns.patterns.*;
import com.example.springaiagenticpatterns.args.ChainWorkflowArgs;
import com.example.springaiagenticpatterns.args.ParelalizationWorkflowArgs;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Scanner;

@SpringBootApplication
@RequiredArgsConstructor
public class Application implements CommandLineRunner {
    private final ChainWorkflow chainWorkflow;
    private final ParelalizationWorkflow parelalizationWorkflow;
    private final RoutingWorkflow routingWorkflow;
    private final OrchestratorWorkers orchestratorWorkers;
    private final EvaluatorOptimizer evaluatorOptimizer;


    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(String... args) {
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
                    chainWorkflow.chain(ChainWorkflowArgs.USER_INPUT);
                }
                case 2 -> {
                    System.out.println("Parallel Workflow: for parallel processing, output of multiple steps are aggregated to produce final output.");
                    parelalizationWorkflow.parallel(ParelalizationWorkflowArgs.PROMPT, ParelalizationWorkflowArgs.USER_INPUTS, 4);
                }
                case 3 -> {
                    System.out.println("Routing Workflow: for specialized processing, different types of inputs are routed to specialized processing steps.");
                    routingWorkflow.route(RoutingWorkflowArgs.supportRoutes, RoutingWorkflowArgs.tickets);
                }
                case 4 -> {
                    System.out.println("Orchestrator-Workers Pattern: complex agent-like behavior while maintaining control over the process, complex tasks where subtasks can't be predicted.");
//                    orchestratorWorkers.process("Write a product description for a new eco-friendly water bottle");
                    orchestratorWorkers.process("Ai driven driving school, where the system will teach the students how to drive a car");
                }
                case 5 -> {
                    System.out.println("Evaluator-Optimizer Pattern: dual LLM process where one LLM model generates a solution and another LLM model evaluates the solution.");
                    evaluatorOptimizer.loop("""
                                                <user input>
                                                Implement a Stack in Java with:
                                                1. push(x)
                                                2. pop()
                                                3. getMin()
                                                All operations should be O(1).
                                                All inner fields should be private and when used should be prefixed with 'this.'.
                                                </user input>
                            """);
                }
                default -> System.out.println("Invalid input");
            }
        }
    }
}

