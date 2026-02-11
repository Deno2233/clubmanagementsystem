package org.example.barmangementsystem.controller;



import org.example.barmangementsystem.dto.OrderReturnItemRequest;
import org.example.barmangementsystem.dto.OrderReturnRequest;
import org.example.barmangementsystem.entity.*;
import org.example.barmangementsystem.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:5173,http://192.168.100.3:4567")





public class OrderReturnController {

    @Autowired private OrderItemRepository orderItemRepository;
    @Autowired private OrderRepository orderRepository;
    @Autowired private ProductRepository productRepository;
    @Autowired private UserRepository userRepository;
    @Autowired private OrderReturnRepository orderReturnRepository;
    @Autowired private RefundPaymentRepository refundPaymentRepository;

    // Get all order items
    @GetMapping("/order-items")
    public List<OrderItemController.OrderItemDto> getOrderItems() {
        return orderItemRepository.findAll().stream()
                .map(oi -> new OrderItemController.OrderItemDto(
                        oi.getId(),
                        oi.getOrder().getId(),
                        oi.getOrder().getOrderTime(),
                        oi.getProduct().getId(),
                        oi.getProduct().getName(),
                        oi.getQuantity(),
                        oi.getReturnedQuantity(),
                        oi.getUnitPrice()
                ))
                .collect(Collectors.toList());
    }

    // Process return
    @PostMapping("/order-returns")
    @Transactional
    public ResponseEntity<?> processReturn(@RequestBody OrderReturnRequest req) {
        Order order = orderRepository.findById(req.getOrderId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Order not found"));

        User user = userRepository.findById(req.getUserId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "User not found"));

        OrderReturn orderReturn = new OrderReturn();
        orderReturn.setOrder(order);
        orderReturn.setProcessedBy(user);

        BigDecimal totalRefund = BigDecimal.ZERO;

        for (OrderReturnItemRequest itemReq : req.getItems()) {
            OrderItem orderItem = orderItemRepository.findById(itemReq.getOrderItemId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Order item not found"));

            if (!orderItem.getOrder().getId().equals(order.getId())) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Item not in order");
            }

            int available = orderItem.getQuantity() - orderItem.getReturnedQuantity();
            if (itemReq.getQuantity() <= 0 || itemReq.getQuantity() > available) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid return qty");
            }

            BigDecimal refund = orderItem.getUnitPrice().multiply(BigDecimal.valueOf(itemReq.getQuantity()));
            totalRefund = totalRefund.add(refund);

            // Update product stock
            Product p = orderItem.getProduct();
            p.setStockQuantity(p.getStockQuantity() + itemReq.getQuantity());
            productRepository.save(p);

            // Update order item
            orderItem.setReturnedQuantity(orderItem.getReturnedQuantity() + itemReq.getQuantity());
            orderItemRepository.save(orderItem);

            // Create OrderReturnItem
            OrderReturnItem ori = new OrderReturnItem();
            ori.setOrderReturn(orderReturn);
            ori.setProduct(p);
            ori.setQuantity(itemReq.getQuantity());
            ori.setRefundAmount(refund);
            orderReturn.getItems().add(ori);
        }

        orderReturn.setTotalRefundAmount(totalRefund);
        orderReturnRepository.save(orderReturn);

        RefundPayment rp = new RefundPayment();
        rp.setOrderReturn(orderReturn);
        rp.setProcessedBy(user);
        rp.setAmount(totalRefund);
        rp.setMethod(req.getRefundMethod() == null ? "CASH" : req.getRefundMethod());
        refundPaymentRepository.save(rp);

        return ResponseEntity.ok(Map.of("message", "Return processed successfully", "refund", totalRefund));
    }
}
