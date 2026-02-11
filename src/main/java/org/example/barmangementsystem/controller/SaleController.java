package org.example.barmangementsystem.controller;


import org.example.barmangementsystem.dto.SaleRequest;
import org.example.barmangementsystem.entity.Order;
import org.example.barmangementsystem.services.SaleService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/sales")
@CrossOrigin(origins = "http://localhost:5173,http://192.168.100.3:4567")
public class SaleController {

    private final SaleService saleService;

    public SaleController(SaleService saleService) {
        this.saleService = saleService;
    }

    @PostMapping
    public Order createSale(@RequestBody SaleRequest request) {
        return saleService.processSale(request);
    }
}
