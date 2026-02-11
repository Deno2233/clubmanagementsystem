package org.example.barmangementsystem.entity;


import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
public class OrderReturnItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "order_return_id")
    private OrderReturn orderReturn;

    @ManyToOne(optional = false)
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(nullable = false)
    private int quantity;

    @Column(nullable = false)
    private BigDecimal refundAmount;
    // getters/setters


    public OrderReturnItem() {
    }

    public OrderReturnItem(Long id, OrderReturn orderReturn, Product product, int quantity, BigDecimal refundAmount) {
        this.id = id;
        this.orderReturn = orderReturn;
        this.product = product;
        this.quantity = quantity;
        this.refundAmount = refundAmount;
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

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getRefundAmount() {
        return refundAmount;
    }

    public void setRefundAmount(BigDecimal refundAmount) {
        this.refundAmount = refundAmount;
    }
}
