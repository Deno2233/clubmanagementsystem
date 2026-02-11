package org.example.barmangementsystem.repository;


import org.example.barmangementsystem.entity.Product;
import org.example.barmangementsystem.entity.StockEntry;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.List;

public interface StockEntryRepository extends JpaRepository<StockEntry, Long> {
    List<StockEntry> findByProduct(Product product);

    List<StockEntry> findByProductOrderByDateReceivedDesc(Product product);

    @Query("SELECT s FROM StockEntry s WHERE s.dateReceived >= CURRENT_DATE ORDER BY s.dateReceived DESC")
    List<StockEntry> findTodayStockEntries(); // Optional, for today's incoming stock

    @Query("SELECT COALESCE(SUM(se.quantityAdded * se.costPerUnit), 0) FROM StockEntry se")
    BigDecimal getTotalCostPrice();

    // Optional: for a specific product
    @Query("SELECT COALESCE(SUM(se.quantityAdded * se.costPerUnit), 0) FROM StockEntry se WHERE se.product.id = :productId")
    BigDecimal getTotalCostPriceByProduct(Long productId);
    // ✅ Get the latest cost per unit for a product
    @Query("SELECT se.costPerUnit FROM StockEntry se WHERE se.product.id = :productId ORDER BY se.dateReceived DESC LIMIT 1")
    BigDecimal findLatestCostPerUnitByProduct(Long productId);
    @Query("SELECT se FROM StockEntry se WHERE se.product.id = :productId ORDER BY se.dateReceived DESC")
    List<StockEntry> findLatestEntryByProduct(Long productId, Pageable pageable);

    List<StockEntry> findByProductId(Long productId);
}
