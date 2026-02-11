package org.example.barmangementsystem.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class OrderReturn {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id")
    private User processedBy;

    private LocalDateTime returnTime = LocalDateTime.now();

    private BigDecimal totalRefundAmount = BigDecimal.ZERO;

    @OneToMany(mappedBy = "orderReturn", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderReturnItem> items = new ArrayList<>();
    // getters/setters

    public OrderReturn() {
    }

    public OrderReturn(Long id, Order order, User processedBy, LocalDateTime returnTime, BigDecimal totalRefundAmount, List<OrderReturnItem> items) {
        this.id = id;
        this.order = order;
        this.processedBy = processedBy;
        this.returnTime = returnTime;
        this.totalRefundAmount = totalRefundAmount;
        this.items = items;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public User getProcessedBy() {
        return processedBy;
    }

    public void setProcessedBy(User processedBy) {
        this.processedBy = processedBy;
    }

    public LocalDateTime getReturnTime() {
        return returnTime;
    }

    public void setReturnTime(LocalDateTime returnTime) {
        this.returnTime = returnTime;
    }

    public BigDecimal getTotalRefundAmount() {
        return totalRefundAmount;
    }

    public void setTotalRefundAmount(BigDecimal totalRefundAmount) {
        this.totalRefundAmount = totalRefundAmount;
    }

    public List<OrderReturnItem> getItems() {
        return items;
    }

    public void setItems(List<OrderReturnItem> items) {
        this.items = items;
    }
}
