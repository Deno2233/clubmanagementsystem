package org.example.barmangementsystem.entity;



import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "order_id")
@JsonIgnore
    private Order order;

    @Column(nullable = false)
    private BigDecimal amount;

    @Column(nullable = false)
    private String method; // CASH, CARD, MOBILE_MONEY, etc.

    @Column(nullable = false)
    private LocalDateTime paidAt = LocalDateTime.now();

    // getters and setters

    public Payment() {
    }

    public Payment(Long id, Order order, BigDecimal amount, String method, LocalDateTime paidAt) {
        this.id = id;
        this.order = order;
        this.amount = amount;
        this.method = method;
        this.paidAt = paidAt;
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

    public LocalDateTime getPaidAt() {
        return paidAt;
    }

    public void setPaidAt(LocalDateTime paidAt) {
        this.paidAt = paidAt;
    }
}
