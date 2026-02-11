package org.example.barmangementsystem.controller;

import org.example.barmangementsystem.entity.OrderItem;
import org.example.barmangementsystem.repository.OrderItemRepository;
import org.example.barmangementsystem.services.OrderItemService;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/order-items")
@CrossOrigin(origins = "http://localhost:5173,http://192.168.100.3:4567")
public class OrderItemController {

    private final OrderItemService orderItemService;
    private final OrderItemRepository orderItemRepository;
    public OrderItemController(OrderItemService orderItemService, OrderItemRepository orderItemRepository) {
        this.orderItemService = orderItemService;
        this.orderItemRepository = orderItemRepository;
    }
    public record OrderItemDto(
            Long id,
            Long orderId,
            LocalDateTime orderTime,
            Long productId,
            String productName,
            int quantity,
            int returnedQuantity,
            BigDecimal unitPrice
    ) {}

    @PostMapping
    public List<OrderItem> create(@RequestBody List<OrderItem> item) {
        return orderItemService.save(item);
    }
    // ✅ Fetch all order items by customer ID
    @GetMapping("/customer/{customerId}")
    public List<OrderItem> getOrderItemsByCustomerId(@PathVariable Long customerId) {
        return orderItemRepository.findByOrder_Customer_Id(customerId);
    }
    @GetMapping("/api/order-items")
    public List<OrderItemDto> getOrderItems() {
        return orderItemRepository.findAll().stream()
                .map(oi -> new OrderItemDto(
                        oi.getId(),
                        oi.getOrder().getId(),
                        oi.getOrder().getOrderTime(),
                        oi.getProduct().getId(),
                        oi.getProduct().getName(),
                        oi.getQuantity(),
                        oi.getReturnedQuantity(),
                        oi.getUnitPrice()))
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public OrderItem getOne(@PathVariable Long id) {
        return orderItemService.findById(id)
                .orElseThrow(() -> new RuntimeException("OrderItem not found"));
    }

    @GetMapping("/by-order/{orderId}")
    public List<OrderItem> getByOrder(@PathVariable Long orderId) {
        return orderItemService.findByOrderId(orderId);
    }

    @PutMapping("/{id}")
    public OrderItem update(@PathVariable Long id, @RequestBody OrderItem updated) {
        return orderItemService.update(id, updated);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        orderItemService.delete(id);
    }
}
