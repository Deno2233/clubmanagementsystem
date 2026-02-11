package org.example.barmangementsystem.services;


import org.example.barmangementsystem.entity.Product;
import org.example.barmangementsystem.entity.StockMovement;
import org.example.barmangementsystem.repository.ProductRepository;
import org.example.barmangementsystem.repository.StockMovementRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class StockMovementService {

    private final StockMovementRepository stockMovementRepo;
    private final ProductRepository productRepo;

    public StockMovementService(StockMovementRepository stockMovementRepo,
                                ProductRepository productRepo) {
        this.stockMovementRepo = stockMovementRepo;
        this.productRepo = productRepo;
    }

    public List<StockMovement> getAllMovements() {
        return stockMovementRepo.findAllByOrderByDateDesc();
    }

    public List<StockMovement> getByProduct(Long productId) {
        Product product = productRepo.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        return stockMovementRepo.findByProductOrderByDateDesc(product);
    }

    public List<StockMovement> getByDateRange(LocalDateTime start, LocalDateTime end) {
        return stockMovementRepo.findByDateBetweenOrderByDateDesc(start, end);
    }

    public List<StockMovement> getByProductAndDateRange(Long productId, LocalDateTime start, LocalDateTime end) {
        Product product = productRepo.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        return stockMovementRepo.findByProductAndDateBetweenOrderByDateDesc(product, start, end);
    }
}

