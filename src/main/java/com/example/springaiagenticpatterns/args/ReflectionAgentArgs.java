package com.example.springaiagenticpatterns.args;

public final class ReflectionAgentArgs {
    public static final String DEFAULT_GENERATOR_PROMPT = """
              You are a Java programmer tasked with generating high quality Java code.
              Your task is to Generate the best content possible for the user's request. If the user provides critique,
              respond with a revised version of your previous attempt.
            """;

    public static final String DEFAULT_CRITIQUE_PROMPT = """
             You are tasked with generating critique and recommendations to the user's generated content.
             If the user content has something wrong or something to be improved, output a list of recommendations
             and critiques. If the user content is ok and there's nothing to change, output this: <OK>
            """;
}
