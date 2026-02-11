package org.example.barmangementsystem.entity;



import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
public class StockMovement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    private Product product;

    @Column(nullable = false)
    private int quantityChange; // positive = IN, negative = OUT

    @Enumerated(EnumType.STRING)
    private StockMovementType type; // IN or OUT

    private String reference; // e.g. Order ID, Stock Entry ID, etc.

    private String reason;

    private BigDecimal costPerUnit;

    private LocalDateTime date = LocalDateTime.now();

    @ManyToOne
    private User handledBy;

    // getters and setters

    public StockMovementType getType() {
        return type;
    }

    public void setType(StockMovementType type) {
        this.type = type;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Product getProduct() { return product; }
    public void setProduct(Product product) { this.product = product; }

    public int getQuantityChange() { return quantityChange; }
    public void setQuantityChange(int quantityChange) { this.quantityChange = quantityChange; }


    public String getReference() { return reference; }
    public void setReference(String reference) { this.reference = reference; }

    public String getReason() { return reason; }
    public void setReason(String reason) { this.reason = reason; }

    public BigDecimal getCostPerUnit() { return costPerUnit; }
    public void setCostPerUnit(BigDecimal costPerUnit) { this.costPerUnit = costPerUnit; }

    public LocalDateTime getDate() { return date; }
    public void setDate(LocalDateTime date) { this.date = date; }

    public User getHandledBy() { return handledBy; }
    public void setHandledBy(User handledBy) { this.handledBy = handledBy; }
}
