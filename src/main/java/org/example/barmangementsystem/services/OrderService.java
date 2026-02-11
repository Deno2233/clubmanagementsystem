package org.example.barmangementsystem.services;


import org.example.barmangementsystem.entity.BarSession;
import org.example.barmangementsystem.entity.Order;
import org.example.barmangementsystem.entity.Payment;
import org.example.barmangementsystem.repository.BarSessionRepository;
import org.example.barmangementsystem.repository.OrderRepository;
import org.example.barmangementsystem.repository.PaymentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final BarSessionRepository barSessionRepository;
    private final PaymentRepository paymentRepository;
    public OrderService(OrderRepository orderRepository, BarSessionRepository barSessionRepository, PaymentRepository paymentRepository) {
        this.orderRepository = orderRepository;
        this.barSessionRepository = barSessionRepository;
        this.paymentRepository = paymentRepository;
    }
    public List<Order> getOrdersByCustomerId(Long customerId) {
        return orderRepository.findByCustomer_Id(customerId);
    }
    @Transactional
    public Order createOrder(Order order) {
        System.out.println("Order created");
        // ✅ Fetch current open bar session
       BarSession currentSession = barSessionRepository.findByClosedFalse()
                .orElseThrow(() -> new IllegalStateException("No active bar session found! Please open a session before creating orders."));

        // ✅ Link this order to the open session
        order.setBarSession(currentSession );


        System.out.println("✅ Assigned session ID: " + currentSession.getId());


        //order.setOrderTime(LocalDateTime.now());

        // ✅ Save order
        return orderRepository.save(order);
    }

    public List<Order> findAll() {
        return orderRepository.findAll();
    }
    public List<Order> getDailyOrders() {
        LocalDate today = LocalDate.now();
        LocalDateTime start = today.atStartOfDay();
        LocalDateTime end = today.atTime(LocalTime.MAX);
        return orderRepository.findByOrderTimeBetween(start, end);
    }
    public Optional<Order> findById(Long id) {
        return orderRepository.findById(id);
    }

    public Order update(Long id, Order updated) {
        return orderRepository.findById(id)
                .map(existing -> {
                    existing.setCustomer(updated.getCustomer());
                    existing.setUser(updated.getUser());
                    existing.setOrderTime(updated.getOrderTime());
                    existing.setItems(updated.getItems());
                    existing.setPayment(updated.getPayment());
                    return orderRepository.save(existing);
                })
                .orElseThrow(() -> new RuntimeException("Order not found"));
    }

    public void delete(Long id) {
        orderRepository.deleteById(id);
    }
}
