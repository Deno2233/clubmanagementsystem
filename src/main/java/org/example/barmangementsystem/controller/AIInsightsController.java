package org.example.barmangementsystem.controller;


import org.example.barmangementsystem.services.AIInsightsService;
import org.example.barmangementsystem.services.SaleService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ai")
@CrossOrigin(origins = "http://localhost:5173,http://192.168.100.3:4567")
public class AIInsightsController {

    private final SaleService saleService;
    private final AIInsightsService aiInsightsService;

    public AIInsightsController(SaleService saleService, AIInsightsService aiInsightsService) {
        this.saleService = saleService;
        this.aiInsightsService = aiInsightsService;
    }

    @GetMapping("/insights")
    public String getAIInsights() {
        var recentSales = saleService.getRecentSalesSummary();
        return aiInsightsService.generateInsights (recentSales);
    }
}
