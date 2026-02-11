package org.example.barmangementsystem.entity;



import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import org.hibernate.annotations.Fetch;

import java.time.LocalDateTime;
import java.util.*;

@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    @JsonBackReference
    @JsonIgnore
    private Customer customer; // nullable for walk-ins

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user; // cashier/bartender handling the order

    @Column(nullable = false)
    private LocalDateTime orderTime = LocalDateTime.now();

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<OrderItem> items = new ArrayList<>();

    @OneToOne(mappedBy = "order", cascade = CascadeType.ALL)
@JsonIgnore
    private Payment payment;
    @ManyToOne
    @JoinColumn(name = "bar_session_id")
@JsonIgnore
    private BarSession barSession;


    // getters and setters

    public Order() {
    }

    public Order(Long id, Customer customer, User user, LocalDateTime orderTime, List<OrderItem> items, Payment payment, BarSession barSession) {
        this.id = id;
        this.customer = customer;
        this.user = user;
        this.orderTime = orderTime;
        this.items = items;
        this.payment = payment;
        this.barSession = barSession;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDateTime getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(LocalDateTime orderTime) {
        this.orderTime = orderTime;
    }

    public List<OrderItem> getItems() {
        return items;
    }

    public void setItems(List<OrderItem> items) {
        this.items = items;
    }

    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    public BarSession getBarSession() {
        return barSession;
    }

    public void setBarSession(BarSession barSession) {
        this.barSession = barSession;
    }
}
