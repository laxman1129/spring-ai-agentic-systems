package com.example.springaiagenticpatterns.patterns;

import com.example.springaiagenticpatterns.args.OrchestratorWorkerArgs;
import com.example.springaiagenticpatterns.records.orchestratorWorker.FinalResponse;
import com.example.springaiagenticpatterns.records.orchestratorWorker.OrchestratorResponse;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.List;

/**
 * this is flexible approach for handling complex tasks that require dynamic task decomposition and specialized processing.
 * 3 main components:
 * 1.`Orchestrator` - main LLM that analyses tasks and determines required subtasks.
 * 2.`Workers` - specialized LLM that executes specific subtasks.
 * 3. `Synthesizer` - LLM that aggregates/composes the results of subtasks into a final output.
 */
@Component
public class OrchestratorWorkers {
    private final ChatClient chatClient;

    public OrchestratorWorkers(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder.build();
    }

    public void process(String taskDescription) {
        Assert.hasText(taskDescription, "Task description must not be empty");
// Step 1: Get orchestrator response
        OrchestratorResponse orchestratorResponse = this.chatClient.prompt()
                .user(u -> u.text(OrchestratorWorkerArgs.DEFAULT_ORCHESTRATOR_PROMPT)
                        .param("task", taskDescription))
                .call()
                .entity(OrchestratorResponse.class);

        assert orchestratorResponse != null;
        System.out.printf("\n=== ORCHESTRATOR OUTPUT ===\nANALYSIS: %s\n\nTASKS: %s\n%n",
                orchestratorResponse.analysis(), orchestratorResponse.tasks());

        // Step 2: Process each task
        List<String> workerResponses = orchestratorResponse.tasks().stream().map(task -> this.chatClient.prompt()
                .user(u -> u.text(OrchestratorWorkerArgs.DEFAULT_WORKER_PROMPT)
                        .param("original_task", taskDescription)
                        .param("task_type", task.type())
                        .param("task_description", task.description()))
                .call()
                .content()).toList();

        System.out.println("\n=== WORKER OUTPUT ===\n" + workerResponses);

        var result = new FinalResponse(orchestratorResponse.analysis(), workerResponses);
        System.out.printf("\n=== FINAL OUTPUT ===\n%s\n", result);
    }
}
