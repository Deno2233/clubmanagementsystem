package org.example.barmangementsystem.entity;


import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
@Entity
public class RefundPayment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "order_return_id")
    private OrderReturn orderReturn;

    @Column(nullable = false)
    private BigDecimal amount;

    @Column(nullable = false)
    private String method;

    private LocalDateTime refundedAt = LocalDateTime.now();

    @ManyToOne(optional = false)
    @JoinColumn(name = "processed_by_id")
    private User processedBy;
    // getters/setters

    public RefundPayment() {
    }

    public RefundPayment(Long id, OrderReturn orderReturn, BigDecimal amount, String method, LocalDateTime refundedAt, User processedBy) {
        this.id = id;
        this.orderReturn = orderReturn;
        this.amount = amount;
        this.method = method;
        this.refundedAt = refundedAt;
        this.processedBy = processedBy;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public OrderReturn getOrderReturn() {
        return orderReturn;
    }

    public void setOrderReturn(OrderReturn orderReturn) {
        this.orderReturn = orderReturn;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public LocalDateTime getRefundedAt() {
        return refundedAt;
    }

    public void setRefundedAt(LocalDateTime refundedAt) {
        this.refundedAt = refundedAt;
    }

    public User getProcessedBy() {
        return processedBy;
    }

    public void setProcessedBy(User processedBy) {
        this.processedBy = processedBy;
    }
}
