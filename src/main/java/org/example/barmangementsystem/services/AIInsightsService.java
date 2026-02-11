package org.example.barmangementsystem.services;


import org.example.barmangementsystem.dto.SaleSummary;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Map;

@Service
public class AIInsightsService {

    private final WebClient webClient;

    public AIInsightsService() {
        this.webClient = WebClient.builder()
                .baseUrl("https://api.openai.com/v1")
                .defaultHeader("Authorization", "Bearer " + System.getenv("OPENAI_API_KEY"))
                .build();
    }

    public String generateInsights(List<SaleSummary> salesData) {
        StringBuilder summary = new StringBuilder("Sales Summary (last 7 days):\n");
        for (SaleSummary s : salesData) {
            summary.append(String.format(
                    "Product: %s | Category: %s | Quantity: %d | Revenue: %.2f\n",
                    s.getProductName(), s.getCategoryName(), s.getTotalQuantity(), s.getTotalSales()
            ));
        }
        System.out.println("OpenAI Key: " + System.getenv("OPENAI_API_KEY"));

        String prompt = """
        Analyze this bar sales summary and provide insights on:
        - Top and low-performing products.
        """ + summary;

        Map<String, Object> response = webClient.post()
                .uri("/chat/completions")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(Map.of(
                        "model", "gpt-3.5-turbo",
                        "messages", List.of(
                                Map.of("role", "system", "content", "You are an expert sales analyst."),
                                Map.of("role", "user", "content", prompt)
                        )
                ))
                .retrieve()
                .bodyToMono(Map.class)
                .block();

        // Extract the AI response text
        Map firstChoice = (Map) ((List) response.get("choices")).get(0);
        Map message = (Map) firstChoice.get("message");
        return message.get("content").toString();
    }
}


