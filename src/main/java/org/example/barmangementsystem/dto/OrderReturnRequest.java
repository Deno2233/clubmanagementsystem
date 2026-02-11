package org.example.barmangementsystem.dto;



import java.util.List;

public class OrderReturnRequest {
    private Long orderId;
    private Long userId;
    private List<OrderReturnItemRequest> items;
    private String refundMethod;
    // getters and setters

    public String getRefundMethod() {
        return refundMethod;
    }

    public void setRefundMethod(String refundMethod) {
        this.refundMethod = refundMethod;
    }

    public OrderReturnRequest() {
    }

    public OrderReturnRequest(Long orderId, Long userId, List<OrderReturnItemRequest> items) {
        this.orderId = orderId;
        this.userId = userId;
        this.items = items;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public List<OrderReturnItemRequest> getItems() {
        return items;
    }

    public void setItems(List<OrderReturnItemRequest> items) {
        this.items = items;
    }
}


