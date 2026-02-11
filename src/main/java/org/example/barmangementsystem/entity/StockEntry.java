package org.example.barmangementsystem.entity;


import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
public class StockEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    private Product product;

    @Column(nullable = false)
    private int quantityAdded;

    @Column(nullable = false)
    private BigDecimal costPerUnit;

    private LocalDateTime dateReceived = LocalDateTime.now();

    @ManyToOne
    private User receivedBy; // Staff who received the stock

    // Getters & Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Product getProduct() { return product; }
    public void setProduct(Product product) { this.product = product; }

    public int getQuantityAdded() { return quantityAdded; }
    public void setQuantityAdded(int quantityAdded) { this.quantityAdded = quantityAdded; }

    public BigDecimal getCostPerUnit() { return costPerUnit; }
    public void setCostPerUnit(BigDecimal costPerUnit) { this.costPerUnit = costPerUnit; }

    public LocalDateTime getDateReceived() { return dateReceived; }
    public void setDateReceived(LocalDateTime dateReceived) { this.dateReceived = dateReceived; }

    public User getReceivedBy() { return receivedBy; }
    public void setReceivedBy(User receivedBy) { this.receivedBy = receivedBy; }
}
