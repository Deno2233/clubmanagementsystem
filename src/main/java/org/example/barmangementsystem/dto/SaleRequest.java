package org.example.barmangementsystem.dto;



import java.math.BigDecimal;
import java.util.List;

public class SaleRequest {

    private Long customerId;   // optional
    private Long userId;       // cashier/bartender
    private String paymentMethod;
    private BigDecimal amount;
    private List<Item> items;

    public static class Item {
        private Long productId;
        private int quantity;
        private BigDecimal unitPrice;
        // getters & setters
        public Long getProductId() { return productId; }
        public void setProductId(Long productId) { this.productId = productId; }
        public int getQuantity() { return quantity; }
        public void setQuantity(int quantity) { this.quantity = quantity; }
        public BigDecimal getUnitPrice() { return unitPrice; }
        public void setUnitPrice(BigDecimal unitPrice) { this.unitPrice = unitPrice; }
    }

    // getters & setters for outer class
    public Long getCustomerId() { return customerId; }
    public void setCustomerId(Long customerId) { this.customerId = customerId; }
    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }
    public String getPaymentMethod() { return paymentMethod; }
    public void setPaymentMethod(String paymentMethod) { this.paymentMethod = paymentMethod; }
    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }
    public List<Item> getItems() { return items; }
    public void setItems(List<Item> items) { this.items = items; }
}
