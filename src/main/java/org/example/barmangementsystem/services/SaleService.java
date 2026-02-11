package org.example.barmangementsystem.services;


import jakarta.transaction.Transactional;
import org.example.barmangementsystem.dto.SaleRequest;
import org.example.barmangementsystem.dto.SaleSummary;
import org.example.barmangementsystem.entity.*;
import org.example.barmangementsystem.repository.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class SaleService {
    private final StockMovementRepository stockMovementRepo;
    private final OrderRepository orderRepo;
    private final OrderItemRepository orderItemRepo;
    private final PaymentRepository paymentRepo;
    private final ProductRepository productRepo;
    private final CustomerRepository customerRepo;
    private final UserRepository userRepo;
    private final BarSessionRepository barSessionRepository;
    public SaleService(StockMovementRepository stockMovementRepo, OrderRepository orderRepo,
                       OrderItemRepository orderItemRepo,
                       PaymentRepository paymentRepo,
                       ProductRepository productRepo,
                       CustomerRepository customerRepo,
                       UserRepository userRepo, BarSessionRepository barSessionRepository) {
        this.stockMovementRepo = stockMovementRepo;
        this.orderRepo = orderRepo;
        this.orderItemRepo = orderItemRepo;
        this.paymentRepo = paymentRepo;
        this.productRepo = productRepo;
        this.customerRepo = customerRepo;
        this.userRepo = userRepo;
        this.barSessionRepository = barSessionRepository;
    }
    @Transactional
    public Order processSale(SaleRequest request) {
        Order order = new Order();

        if (request.getCustomerId() != null) {
            customerRepo.findById(request.getCustomerId()).ifPresent(order::setCustomer);
        }
        if (request.getUserId() != null) {
            userRepo.findById(request.getUserId()).ifPresent(order::setUser);
        }

        BarSession currentSession = barSessionRepository.findByClosedFalse()
                .orElseThrow(() -> new IllegalStateException("No active bar session found! Please open a session before creating orders."));

        order.setBarSession(currentSession);

        List<OrderItem> items = new ArrayList<>();

        for (SaleRequest.Item itemDto : request.getItems()) {
            Product product = productRepo.findById(itemDto.getProductId())
                    .orElseThrow(() -> new RuntimeException("Product not found"));

            if (product.getStockQuantity() < itemDto.getQuantity()) {
                throw new IllegalStateException("Not enough stock for product: " + product.getName());
            }

            // Build order item
            OrderItem oi = new OrderItem();
            oi.setOrder(order);
            oi.setProduct(product);
            oi.setQuantity(itemDto.getQuantity());
            oi.setUnitPrice(itemDto.getUnitPrice());
            items.add(oi);

            // Reduce stock
            product.setStockQuantity(product.getStockQuantity() - itemDto.getQuantity());
            productRepo.save(product);

            // ✅ Log stock movement
            StockMovement movement = new StockMovement();
            movement.setProduct(product);
            movement.setCostPerUnit(product.getCostPrice());
            movement.setQuantityChange(-itemDto.getQuantity());
            movement.setType(StockMovementType.OUT);

            movement.setReference("Order #" + order.getId()); // might be null before save
            movement.setReason("Sale");
            movement.setHandledBy(order.getUser());
            stockMovementRepo.save(movement);
        }

        order.setItems(items);

        // Save order
        Order savedOrder = orderRepo.save(order);

        // Now update reference for StockMovement (optional)
        for (StockMovement sm : stockMovementRepo.findByReference("Order #null")) {
            sm.setReference("Order #" + savedOrder.getId());
            stockMovementRepo.save(sm);
        }

        // Payment
        Payment payment = new Payment();
        payment.setOrder(savedOrder);
        payment.setAmount(request.getAmount());
        payment.setMethod(request.getPaymentMethod());
        paymentRepo.save(payment);

        savedOrder.setPayment(payment);

        return savedOrder;
    }

    public List<SaleSummary> getRecentSalesSummary() {
        LocalDateTime startDate = LocalDateTime.now().minusDays(7);

        return orderItemRepo.findSalesSummarySince(startDate);
    }

}

