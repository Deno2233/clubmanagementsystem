package org.example.barmangementsystem.entity;



import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

@Entity
public class BarSession {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id")
    private User openedBy; // cashier or bartender who opened the bar

    private LocalDateTime openingTime = LocalDateTime.now();
    private LocalDateTime closingTime;

    @Column(nullable = false)
    private BigDecimal openingFloat = BigDecimal.ZERO;

    private BigDecimal totalSales = BigDecimal.ZERO;

    private boolean closed = false;

    @OneToMany(mappedBy = "barSession")
    private List<Order> orders = new ArrayList<>();

    // getters and setters

    public BarSession() {
    }

    public BarSession(Long id, User openedBy, LocalDateTime openingTime, LocalDateTime closingTime, BigDecimal openingFloat, BigDecimal totalSales, boolean closed, List<Order> orders) {
        this.id = id;
        this.openedBy = openedBy;
        this.openingTime = openingTime;
        this.closingTime = closingTime;
        this.openingFloat = openingFloat;
        this.totalSales = totalSales;
        this.closed = closed;
        this.orders = orders;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getOpenedBy() {
        return openedBy;
    }

    public void setOpenedBy(User openedBy) {
        this.openedBy = openedBy;
    }

    public LocalDateTime getOpeningTime() {
        return openingTime;
    }

    public void setOpeningTime(LocalDateTime openingTime) {
        this.openingTime = openingTime;
    }

    public LocalDateTime getClosingTime() {
        return closingTime;
    }

    public void setClosingTime(LocalDateTime closingTime) {
        this.closingTime = closingTime;
    }

    public BigDecimal getOpeningFloat() {
        return openingFloat;
    }

    public void setOpeningFloat(BigDecimal openingFloat) {
        this.openingFloat = openingFloat;
    }

    public BigDecimal getTotalSales() {
        return totalSales;
    }

    public void setTotalSales(BigDecimal totalSales) {
        this.totalSales = totalSales;
    }

    public boolean isClosed() {
        return closed;
    }

    public void setClosed(boolean closed) {
        this.closed = closed;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }
}
