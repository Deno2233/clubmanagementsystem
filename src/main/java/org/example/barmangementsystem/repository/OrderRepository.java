package org.example.barmangementsystem.repository;

import org.example.barmangementsystem.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByOrderTimeBetween(LocalDateTime start, LocalDateTime end);
    // Custom query methods if needed
    // Calculate total sales for a specific session
    @Query("SELECT COALESCE(SUM(p.amount), 0) FROM Payment p WHERE p.order.barSession.id = :sessionId")
    BigDecimal sumSalesBySession(Long sessionId);
    List<Order> findByCustomer_Id(Long customerId);
}