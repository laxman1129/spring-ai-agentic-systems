package com.example.springaiagenticpatterns.records.orchestratorWorker;

import java.util.List;

public record FinalResponse(String analysis, List<String> workerResponses) {
}