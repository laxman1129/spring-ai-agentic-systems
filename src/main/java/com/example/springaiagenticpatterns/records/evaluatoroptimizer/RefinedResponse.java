package com.example.springaiagenticpatterns.records.evaluatoroptimizer;

import java.util.List;

public record RefinedResponse(String solution, List<Generation> chainOfThought) {
}