package org.example.barmangementsystem.controller;

import org.example.barmangementsystem.entity.Order;
import org.example.barmangementsystem.services.OrderService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@CrossOrigin(origins = "http://localhost:5173,http://192.168.100.3:4567")
public class OrderController {

    private final OrderService orderService;
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public Order create(@RequestBody Order order) {
        System.out.println("Order created");
        return orderService.createOrder(order);
    }

    @GetMapping
    public List<Order> getAll() {
        return orderService.findAll();
    }
    @GetMapping("/daily")
    public List<Order> getDailyOrders() {
        return orderService.getDailyOrders();
    }

    @GetMapping("/{id}")
    public Order getOne(@PathVariable Long id) {
        return orderService.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));
    }


    @PutMapping("/{id}")
    public Order update(@PathVariable Long id, @RequestBody Order order) {
        return orderService.update(id, order);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        orderService.delete(id);
    }
}