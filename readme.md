# Spring AI Agentic Patterns

## Overview of Agentic Systems

There are two types of systems

- `Workflows`: Systems where LLMs and tools are orchestrated through predefined code paths (e.g., prescriptive system)
- `Agents`: Systems where LLMs dynamically direct their own processes and tool usage

## Agentic Patterns

There are 5 agentic patterns

1. `Chain Workflow` : for sequential processing, output of one step is input to next step
2. `Parallel Workflow` : for parallel processing, output of multiple steps are aggregated to produce final output
3. `Routing Workflow` : for specialized processing, different types of inputs are routed to specialized processing steps
4. `Orchestrator-Workers Pattern` : complex agent-like behavior while maintaining control over the process, complex
   tasks where subtasks cant be predicted
5. `Evaluator-Optimizer Pattern` : dual llm process where one llm model generates a solution and another llm model
   evaluates the solution

---

## References

- https://spring.io/blog/2025/01/21/spring-ai-agentic-patterns
- https://github.com/spring-projects/spring-ai-examples/tree/main/agentic-patterns
