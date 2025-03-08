package com.example.springaiagenticpatterns.records.orchestratorWorker;

import java.util.List;

public record OrchestratorResponse(String analysis, List<Task> tasks) {
}