package org.example.barmangementsystem.services;

import org.example.barmangementsystem.entity.*;
import org.example.barmangementsystem.repository.ProductRepository;
import org.example.barmangementsystem.repository.StockEntryRepository;
import org.example.barmangementsystem.repository.StockMovementRepository;
import org.example.barmangementsystem.repository.UserRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class StockEntryService {
    private final StockMovementRepository stockMovementRepo;
    private final StockEntryRepository stockRepo;
    private final ProductRepository productRepo;
private final UserRepository userRepo;
    public StockEntryService(StockMovementRepository stockMovementRepo, StockEntryRepository stockRepo, ProductRepository productRepo, UserRepository userRepo) {
        this.stockMovementRepo = stockMovementRepo;
        this.stockRepo = stockRepo;
        this.productRepo = productRepo;
        this.userRepo = userRepo;
    }

    @Transactional
    public StockEntry addStock(Long productId, int quantity, BigDecimal costPerUnit, long userId) {
        // 🧩 Validation
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than 0");
        }
        if (costPerUnit.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Cost per unit must be greater than 0");
        }

        Product product = productRepo.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));
User user= userRepo.findById(userId) .orElseThrow(() -> new RuntimeException("User not found"));;
        // Create stock entry
        StockEntry entry = new StockEntry();
        entry.setProduct(product);
        entry.setQuantityAdded(quantity);
        entry.setCostPerUnit(costPerUnit);
        entry.setReceivedBy(user);
       // movement.setType(StockMovementType.IN);
        stockRepo.save(entry);
        StockMovement movement = new StockMovement();
        movement.setProduct(product);
        movement.setCostPerUnit(costPerUnit);
        movement.setQuantityChange(quantity);
        movement.setType(StockMovementType.IN);

        //movement.setReference("); // might be null before save
        movement.setReason("Stock Received");
        movement.setHandledBy(user);
        stockMovementRepo.save(movement);
        // Update product quantity and cost price
        product.setStockQuantity(product.getStockQuantity() + quantity);
        product.setCostPrice(costPerUnit);
        productRepo.save(product);

        return entry;
    }

    public List<StockEntry> getAllEntries() {
        return stockRepo.findAll();
    }

    public List<StockEntry> getByProduct(Long productId) {
        Product product = productRepo.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        return stockRepo.findByProductOrderByDateReceivedDesc(product);
    }

    // 📊 Optional: get today's incoming stock
    public List<StockEntry> getTodayEntries() {
        return stockRepo.findTodayStockEntries();
    }

    // 🔔 Low-stock alert logic
    public List<Product> getLowStockProducts(int threshold) {
        return productRepo.findAll()
                .stream()
                .filter(p -> p.getStockQuantity() <= threshold)
                .toList();
    }


    public BigDecimal getTotalCostPrice() {
        return stockRepo.findAll().stream()
                .map(entry -> entry.getCostPerUnit().multiply(BigDecimal.valueOf(entry.getQuantityAdded())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public BigDecimal getTotalCostPriceByProduct(Long productId) {
        return stockRepo.findAll().stream()
                .filter(entry -> entry.getProduct().getId().equals(productId))
                .map(entry -> entry.getCostPerUnit().multiply(BigDecimal.valueOf(entry.getQuantityAdded())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
    // ✅ Total stock value based on current stock * latest cost per unit
    public BigDecimal getTotalStockValue() {
        List<Product> products = productRepo.findAll();

        return products.stream()
                .map(product -> {
                    List<StockEntry> latestEntry = stockRepo
                            .findLatestEntryByProduct(product.getId(), PageRequest.of(0, 1));

                    BigDecimal latestCost = latestEntry.isEmpty()
                            ? BigDecimal.ZERO
                            : latestEntry.get(0).getCostPerUnit();

                    return latestCost.multiply(BigDecimal.valueOf(product.getStockQuantity()));
                })
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}

