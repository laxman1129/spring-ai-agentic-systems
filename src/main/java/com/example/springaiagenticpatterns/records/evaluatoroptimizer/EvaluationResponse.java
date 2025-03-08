package com.example.springaiagenticpatterns.records.evaluatoroptimizer;

public record EvaluationResponse(Evaluation evaluation, String feedback) {

    public enum Evaluation {
        PASS, NEEDS_IMPROVEMENT, FAIL
    }
}