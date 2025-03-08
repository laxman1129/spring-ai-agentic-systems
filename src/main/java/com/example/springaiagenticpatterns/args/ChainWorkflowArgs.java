package com.example.springaiagenticpatterns.args;

public final class ChainWorkflowArgs {

    /**
     * Array of system prompts that define the transformation steps in the chain.
     * Each prompt acts as a gate that validates and transforms the output before
     * proceeding to the next step.
     */
    public static final String[] DEFAULT_SYSTEM_PROMPTS = {

            // Step 1
            """
					Extract only the numerical values and their associated metrics from the text.
					Format each as'value: metric' on a new line.
					Example format:
					92: customer satisfaction
					45%: revenue growth""",
            // Step 2
            """
					Convert all numerical values to percentages where possible.
					If not a percentage or points, convert to decimal (e.g., 92 points -> 92%).
					Keep one number per line.
					Example format:
					92%: customer satisfaction
					45%: revenue growth""",
            // Step 3
            """
					Sort all lines in descending order by numerical value.
					Keep the format 'value: metric' on each line.
					Example:
					92%: customer satisfaction
					87%: employee satisfaction""",
            // Step 4
            """
					Format the sorted data as a markdown table with columns:
					| Metric | Value |
					|:--|--:|
					| Customer Satisfaction | 92% | """
    };

    public static final String USER_INPUT = """
            Q3 Performance Summary:
            Our customer satisfaction score rose to 92 points this quarter.
            Revenue grew by 45% compared to last year.
            Market share is now at 23% in our primary market.
            Customer churn decreased to 5% from 8%.
            New user acquisition cost is $43 per user.
            Product adoption rate increased to 78%.
            Employee satisfaction is at 87 points.
            Operating margin improved to 34%.
            """;
}
