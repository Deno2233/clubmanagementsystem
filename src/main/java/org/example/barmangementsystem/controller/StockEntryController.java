package org.example.barmangementsystem.controller;


import org.example.barmangementsystem.entity.Product;
import org.example.barmangementsystem.entity.StockEntry;
import org.example.barmangementsystem.entity.User;
import org.example.barmangementsystem.repository.StockEntryRepository;
import org.example.barmangementsystem.services.StockEntryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.security.Principal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/stock")
@CrossOrigin(origins = "http://localhost:5173,http://192.168.100.3:4567")
public class StockEntryController {

    @Autowired
    private StockEntryService stockService;

    private final StockEntryRepository stockRepo;

    public StockEntryController(StockEntryRepository stockRepo) {
        this.stockRepo = stockRepo;
    }

    // DTO for incoming request
    public static class AddStockRequest {
        public Long productId;
        public int quantity;
        public BigDecimal costPerUnit;
        public long userId;
    }

    @PostMapping("/add")
    public ResponseEntity<?> addStock(@RequestBody AddStockRequest request, Principal principal) {
        try {


            StockEntry entry = stockService.addStock(
                    request.productId,
                    request.quantity,
                    request.costPerUnit,
                    request.userId
            );

            return ResponseEntity.ok(entry);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        } catch (RuntimeException ex) {
            return ResponseEntity.status(404).body(ex.getMessage());
        }
    }

    // 📊 Get stock history for a product
    @GetMapping("/product/{productId}")
    public List<StockEntry> getProductStockHistory(@PathVariable Long productId) {
        return stockService.getByProduct(productId);
    }

    // 📊 Get today's incoming stock (optional)
    @GetMapping("/today")
    public List<StockEntry> getTodayStock() {
        return stockService.getTodayEntries();
    }

    // 🔔 Low stock alerts
    @GetMapping("/alerts/low-stock")
    public List<Product> getLowStockAlerts(@RequestParam(defaultValue = "5") int threshold) {
        return stockService.getLowStockProducts(threshold);
    }
    @GetMapping("/total-cost")
    public ResponseEntity<BigDecimal> getTotalCost() {
        return ResponseEntity.ok(stockService.getTotalCostPrice());
    }

    @GetMapping("/total-cost/{productId}")
    public ResponseEntity<BigDecimal> getTotalCostByProduct(@PathVariable Long productId) {
        return ResponseEntity.ok(stockService.getTotalCostPriceByProduct(productId));
    }
    // ✅ New endpoint for total stock value
    @GetMapping("/total-stock-value")
    public ResponseEntity<BigDecimal> getTotalStockValue() {
        return ResponseEntity.ok(stockService.getTotalStockValue());
    }

    @GetMapping("/daily-totals")
    public List<Map<String, Object>> getDailyStockTotals() {
        List<StockEntry> entries = stockRepo.findAll();

        // Group by date and sum total cost (quantityAdded * costPerUnit)
        Map<LocalDate, BigDecimal> totals = entries.stream()
                .collect(Collectors.groupingBy(
                        e -> e.getDateReceived().toLocalDate(),
                        Collectors.reducing(
                                BigDecimal.ZERO,
                                e -> e.getCostPerUnit().multiply(BigDecimal.valueOf(e.getQuantityAdded())),
                                BigDecimal::add
                        )
                ));

        // Convert to list of {date, totalCost}
        return totals.entrySet().stream()
                .sorted(Map.Entry.<LocalDate, BigDecimal>comparingByKey().reversed()) // newest first
                .map(entry -> {
                    Map<String, Object> map = new HashMap<>();
                    map.put("date", entry.getKey());
                    map.put("totalCost", entry.getValue());
                    return map;
                })
                .collect(Collectors.toList());
    }
}
