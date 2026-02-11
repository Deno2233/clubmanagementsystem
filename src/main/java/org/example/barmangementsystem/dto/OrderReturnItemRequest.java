package org.example.barmangementsystem.dto;
public class OrderReturnItemRequest {
    private Long orderItemId;
    private int quantity;
    // getters/setters

    public Long getOrderItemId() {
        return orderItemId;
    }

    public void setOrderItemId(Long orderItemId) {
        this.orderItemId = orderItemId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}