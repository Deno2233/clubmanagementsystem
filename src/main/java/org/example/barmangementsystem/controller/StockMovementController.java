package org.example.barmangementsystem.controller;


import org.example.barmangementsystem.entity.Product;
import org.example.barmangementsystem.entity.StockMovement;
import org.example.barmangementsystem.repository.ProductRepository;
import org.example.barmangementsystem.repository.StockMovementRepository;
import org.example.barmangementsystem.services.StockMovementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;

@RestController
@RequestMapping("/api/stock-movements")
@CrossOrigin(origins = "http://localhost:5173,http://192.168.100.3:4567")

public class StockMovementController {

    private final StockMovementService stockMovementService;

    public StockMovementController(StockMovementService stockMovementService) {
        this.stockMovementService = stockMovementService;
    }

    // ✅ Get all movements
    @GetMapping
    public List<StockMovement> getAllMovements() {
        return stockMovementService.getAllMovements();
    }

    // ✅ Filter by product
    @GetMapping("/product/{productId}")
    public List<StockMovement> getByProduct(@PathVariable Long productId) {
        return stockMovementService.getByProduct(productId);
    }

    // ✅ Filter by date range
    @GetMapping("/filter/by-date")
    public List<StockMovement> getByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end) {
        return stockMovementService.getByDateRange(start, end);
    }

    // ✅ Filter by product + date range
    @GetMapping("/filter/by-product-and-date")
    public List<StockMovement> getByProductAndDate(
            @RequestParam Long productId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end) {
        return stockMovementService.getByProductAndDateRange(productId, start, end);
    }
}



