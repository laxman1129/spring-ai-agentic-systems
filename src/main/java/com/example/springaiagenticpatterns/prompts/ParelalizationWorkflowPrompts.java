package com.example.springaiagenticpatterns.prompts;

import java.util.List;

public final class ParelalizationWorkflowPrompts {
    public static final String PROMPT = """
            Analyze how market changes will impact this stakeholder group.
            Provide specific impacts and recommended actions.
            Format with clear sections and priorities.
            """;
    public static final List<String> USER_INPUTS = List.of(
            """
                    Customers:
                    - Price sensitive
                    - Want better tech
                    - Environmental concerns
           """,

            """
                    Employees:
                    - Job security worries
                    - Need new skills
                    - Want clear direction
                    """,

            """
                    Investors:
                    - Expect growth
                    - Want cost control
                    - Risk concerns
                    """,

            """
                    Suppliers:
                    - Capacity constraints
                    - Price pressures
                    - Tech transitions
                    """
    );
}
