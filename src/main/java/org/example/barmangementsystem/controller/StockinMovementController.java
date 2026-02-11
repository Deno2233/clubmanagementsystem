package org.example.barmangementsystem.controller;



import org.example.barmangementsystem.entity.StockEntry;
import org.example.barmangementsystem.repository.ProductRepository;

import org.example.barmangementsystem.repository.StockEntryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

@RestController
@RequestMapping("/api/instock-movements")
@CrossOrigin(origins = "http://localhost:5173,http://192.168.100.3:4567")
public class StockinMovementController {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private StockEntryRepository stockEntryRepository;



    /**
     * ✅ Get all stock movements or filter by product and date
     */
    @GetMapping
    public List<Map<String, Object>> getStockMovements(
            @RequestParam(required = false) Long productId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end
    ) {

        List<Map<String, Object>> allMovements = new ArrayList<>();

        // 📦 Fetch Stock Entries (Incoming)
        List<StockEntry> entries = (productId != null)
                ? stockEntryRepository.findByProductId(productId)
                : stockEntryRepository.findAll();

        for (StockEntry entry : entries) {
            if (filterByDate(entry.getDateReceived(), start, end)) {
                Map<String, Object> map = new LinkedHashMap<>();
                map.put("date", entry.getDateReceived());
                map.put("type", "IN");
                map.put("product", entry.getProduct());
                map.put("quantityChange", entry.getQuantityAdded());
                map.put("reason", "Stock Received");
                map.put("costPerUnit", entry.getCostPerUnit());
                map.put("handledBy", entry.getReceivedBy());
                allMovements.add(map);
            }
        }

        // Sort by date descending
        allMovements.sort((a, b) -> ((LocalDateTime) b.get("date")).compareTo((LocalDateTime) a.get("date")));

        return allMovements;
    }

    /**
     * ✅ Get stock summary by product and date range
     */
    @GetMapping("/summary")
    public Map<String, Object> getStockSummary(
            @RequestParam(required = false) Long productId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end
    ) {
        Map<String, Object> summary = new HashMap<>();

        BigDecimal totalIn = BigDecimal.ZERO;
        BigDecimal totalOut = BigDecimal.ZERO;
        BigDecimal totalAdjustments = BigDecimal.ZERO;

        // Stock In from entries
        List<StockEntry> entries = (productId != null)
                ? stockEntryRepository.findByProductId(productId)
                : stockEntryRepository.findAll();

        for (StockEntry entry : entries) {
            if (filterByDate(entry.getDateReceived(), start, end)) {
                totalIn = totalIn.add(BigDecimal.valueOf(entry.getQuantityAdded()));
            }
        }


        // Closing balance = totalIn + adjustments - totalOut
        BigDecimal closing = totalIn.add(totalAdjustments).subtract(totalOut);

        summary.put("totalIn", totalIn);
        summary.put("totalOut", totalOut);
        summary.put("totalAdjustments", totalAdjustments);
        summary.put("closingBalance", closing);

        return summary;
    }

    /**
     * Helper method for date filtering
     */
    private boolean filterByDate(LocalDateTime date, LocalDateTime start, LocalDateTime end) {
        if (start == null && end == null) return true;
        if (start != null && date.isBefore(start)) return false;
        if (end != null && date.isAfter(end)) return false;
        return true;
    }
}
