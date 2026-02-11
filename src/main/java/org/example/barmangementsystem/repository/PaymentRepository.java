package org.example.barmangementsystem.repository;

import org.example.barmangementsystem.dto.DailyPaymentDTO;
import org.example.barmangementsystem.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
    // Find payment for a specific order
    java.util.Optional<Payment> findByOrderId(Long orderId);

    List<Payment> findByPaidAtBetween(LocalDateTime localDateTime, LocalDateTime localDateTime1);
    @Query("SELECT new org.example.barmangementsystem.dto.DailyPaymentDTO(DATE(p.paidAt), SUM(p.amount)) " +
            "FROM Payment p " +
            "GROUP BY DATE(p.paidAt) " +
            "ORDER BY DATE(p.paidAt)")
    List<DailyPaymentDTO> getDailyPayments();
    @Query("SELECT SUM(p.amount) FROM Payment p WHERE p.order.barSession.id = :sessionId")
    Optional<BigDecimal> sumPaymentsBySession(Long sessionId);

}