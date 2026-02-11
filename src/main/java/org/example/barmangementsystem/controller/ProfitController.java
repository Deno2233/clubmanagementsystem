package org.example.barmangementsystem.controller;


import org.example.barmangementsystem.dto.CategoryProfitDTO;
import org.example.barmangementsystem.dto.ProductProfitDTO;
import org.example.barmangementsystem.dto.ProfitTrendDTO;
import org.example.barmangementsystem.dto.SessionProfitDTO;
import org.example.barmangementsystem.services.ProfitService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/profits")
@CrossOrigin(origins = "http://localhost:5173,http://192.168.100.3:4567")
public class ProfitController {

    private final ProfitService profitService;

    public ProfitController(ProfitService profitService) {
        this.profitService = profitService;
    }

    // ===================== PRODUCT PROFIT =====================
    @GetMapping("/products")
    public List<ProductProfitDTO> getProductProfits() {
        return profitService.getProductProfits();
    }

    // ===================== CATEGORY PROFIT =====================
    @GetMapping("/categories")
    public List<CategoryProfitDTO> getCategoryProfits() {
        return profitService.getCategoryProfits();
    }

    // ===================== BAR SESSION PROFIT =====================
    @GetMapping("/sessions")
    public List<SessionProfitDTO> getSessionProfits() {
        return profitService.getSessionProfits();
    }

    // ===================== DAILY TREND =====================
    @GetMapping("/trends/daily")
    public List<ProfitTrendDTO> getDailyProfitTrend() {
        return profitService.getDailyProfitTrend();
    }

    // ===================== WEEKLY TREND =====================
    @GetMapping("/trends/weekly")
    public List<ProfitTrendDTO> getWeeklyProfitTrend() {
        return profitService.getWeeklyProfitTrend();
    }

    // ===================== MONTHLY TREND =====================
    @GetMapping("/trends/monthly")
    public List<ProfitTrendDTO> getMonthlyProfitTrend() {
        return profitService.getMonthlyProfitTrend();
    }
}
