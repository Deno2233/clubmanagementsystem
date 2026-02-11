package org.example.barmangementsystem.services;



import org.example.barmangementsystem.entity.BarSession;
import org.example.barmangementsystem.entity.User;
import org.example.barmangementsystem.repository.BarSessionRepository;
import org.example.barmangementsystem.repository.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
public class BarSessionService {

    private final BarSessionRepository barSessionRepository;
    private final OrderRepository orderRepository;

    public BarSessionService(BarSessionRepository barSessionRepository, OrderRepository orderRepository) {
        this.barSessionRepository = barSessionRepository;
        this.orderRepository = orderRepository;
    }

    public BarSession getCurrentSession() {
        return barSessionRepository.findByClosedFalse().orElse(null);
    }

    @Transactional
    public BarSession openSession(User user, BigDecimal openingFloat) {
        if (barSessionRepository.findByClosedFalse().isPresent()) {
            throw new IllegalStateException("A session is already open!");
        }

        BarSession session = new BarSession();
        session.setOpenedBy(user);
        session.setOpeningFloat(openingFloat);
        return barSessionRepository.save(session);
    }

    @Transactional
    public BarSession closeSession(Long sessionId) {
        BarSession session = barSessionRepository.findById(sessionId)
                .orElseThrow(() -> new IllegalArgumentException("Session not found"));

        if (session.isClosed()) throw new IllegalStateException("Session already closed");

        BigDecimal totalSales = orderRepository.sumSalesBySession(sessionId);
        session.setTotalSales(totalSales);
        session.setClosed(true);
        session.setClosingTime(LocalDateTime.now());

        return barSessionRepository.save(session);
    }
}

