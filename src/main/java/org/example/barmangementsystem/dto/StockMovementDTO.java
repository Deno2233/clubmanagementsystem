package org.example.barmangementsystem.dto;


import java.math.BigDecimal;
import java.time.LocalDateTime;

public class StockMovementDTO {
    private String productName;
    private LocalDateTime date;
    private String type; // IN, OUT, ADJUSTMENT
    private int quantityChange;
    private String reference; // e.g. "StockEntry #5", "Order #22", "Adjustment #9"
    private String reason; // optional
    private BigDecimal costPerUnit;

    // Constructors
    public StockMovementDTO(String productName, LocalDateTime date, String type, int quantityChange, String reference, String reason, BigDecimal costPerUnit) {
        this.productName = productName;
        this.date = date;
        this.type = type;
        this.quantityChange = quantityChange;
        this.reference = reference;
        this.reason = reason;
        this.costPerUnit = costPerUnit;
    }

    // Getters and Setters
    public String getProductName() { return productName; }
    public void setProductName(String productName) { this.productName = productName; }

    public LocalDateTime getDate() { return date; }
    public void setDate(LocalDateTime date) { this.date = date; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public int getQuantityChange() { return quantityChange; }
    public void setQuantityChange(int quantityChange) { this.quantityChange = quantityChange; }

    public String getReference() { return reference; }
    public void setReference(String reference) { this.reference = reference; }

    public String getReason() { return reason; }
    public void setReason(String reason) { this.reason = reason; }

    public BigDecimal getCostPerUnit() { return costPerUnit; }
    public void setCostPerUnit(BigDecimal costPerUnit) { this.costPerUnit = costPerUnit; }
}
