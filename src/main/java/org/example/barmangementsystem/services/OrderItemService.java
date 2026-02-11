package org.example.barmangementsystem.services;

import org.example.barmangementsystem.entity.OrderItem;
import org.example.barmangementsystem.repository.OrderItemRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderItemService {

    private final OrderItemRepository orderItemRepository;
    public OrderItemService(OrderItemRepository orderItemRepository) {
        this.orderItemRepository = orderItemRepository;
    }

    public List<OrderItem> save(List<OrderItem> item) {
        return orderItemRepository.saveAll(item);
    }

    public List<OrderItem> findAll() {
        return orderItemRepository.findAll();
    }

    public Optional<OrderItem> findById(Long id) {
        return orderItemRepository.findById(id);
    }

    public List<OrderItem> findByOrderId(Long orderId) {
        return orderItemRepository.findByOrderId(orderId);
    }

    public OrderItem update(Long id, OrderItem updated) {
        return orderItemRepository.findById(id)
                .map(existing -> {
                    existing.setProduct(updated.getProduct());
                    existing.setQuantity(updated.getQuantity());
                    existing.setUnitPrice(updated.getUnitPrice());
                    return orderItemRepository.save(existing);
                })
                .orElseThrow(() -> new RuntimeException("OrderItem not found"));
    }

    public void delete(Long id) {
        orderItemRepository.deleteById(id);
    }
}
