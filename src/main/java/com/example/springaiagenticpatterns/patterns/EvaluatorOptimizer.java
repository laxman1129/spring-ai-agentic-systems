package com.example.springaiagenticpatterns.patterns;

import com.example.springaiagenticpatterns.args.EvaluatorOptimizerArgs;
import com.example.springaiagenticpatterns.records.evaluatoroptimizer.EvaluationResponse;
import com.example.springaiagenticpatterns.records.evaluatoroptimizer.Generation;
import com.example.springaiagenticpatterns.records.evaluatoroptimizer.RefinedResponse;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class EvaluatorOptimizer {
    private final ChatClient chatClient;

    public EvaluatorOptimizer(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder.build();
    }

    public RefinedResponse loop(String task) {
        List<String> memory = new ArrayList<>();
        List<Generation> chainOfThought = new ArrayList<>();

        return loop(task, "", memory, chainOfThought);
    }

    /**
     * Recursive loop to generate and evaluate responses until the solution is accepted.
     * generate response -> evaluate response -> if pass return response else loop
     *
     * @param task           : The task to solve
     * @param context        : The context to provide to the generator
     * @param memory         : The memory of previous attempts
     * @param chainOfThought : The chain of thoughts
     * @return : The refined response
     */
    private RefinedResponse loop(String task, String context, List<String> memory, List<Generation> chainOfThought) {
        Generation generation = generate(task, context);
        memory.add(generation.response());
        chainOfThought.add(generation);

        EvaluationResponse evaluationResponse = evalute(generation.response(), task);

        if (evaluationResponse.evaluation().equals(EvaluationResponse.Evaluation.PASS)) {
            // Solution is accepted!
            return new RefinedResponse(generation.response(), chainOfThought);
        }

        // Accumulated new context including the last and the previous attempts and
        // feedbacks.
        StringBuilder newContext = new StringBuilder();
        newContext.append("Previous attempts:");
        for (String m : memory) {
            newContext.append("\n- ").append(m);
        }
        newContext.append("\nFeedback: ").append(evaluationResponse.feedback());


        return loop(task, newContext.toString(), memory, chainOfThought);
    }

    private Generation generate(String task, String context) {
        Generation generationResponse = chatClient.prompt()
                .user(u -> u.text("{prompt}\n{context}\nTask: {task}")
                        .param("prompt", EvaluatorOptimizerArgs.DEFAULT_GENERATOR_PROMPT)
                        .param("context", context)
                        .param("task", task))
                .call()
                .entity(Generation.class);

        assert generationResponse != null;
        System.out.printf("\n=== GENERATOR OUTPUT ===\nTHOUGHTS: %s\n\nRESPONSE:\n %s\n%n",
                generationResponse.thoughts(), generationResponse.response());
        return generationResponse;
    }

    private EvaluationResponse evalute(String content, String task) {

        EvaluationResponse evaluationResponse = chatClient.prompt()
                .user(u -> u.text("{prompt}\nOriginal task: {task}\nContent to evaluate: {content}")
                        .param("prompt", EvaluatorOptimizerArgs.DEFAULT_EVALUATOR_PROMPT)
                        .param("task", task)
                        .param("content", content))
                .call()
                .entity(EvaluationResponse.class);

        assert evaluationResponse != null;
        System.out.printf("\n=== EVALUATOR OUTPUT ===\nEVALUATION: %s\n\nFEEDBACK: %s\n%n",
                evaluationResponse.evaluation(), evaluationResponse.feedback());
        return evaluationResponse;
    }
}
