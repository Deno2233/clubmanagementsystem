package org.example.barmangementsystem.services;


import org.example.barmangementsystem.dto.DailyPaymentDTO;
import org.example.barmangementsystem.entity.Payment;
import org.example.barmangementsystem.repository.PaymentRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import java.util.Optional;

@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;
    public PaymentService(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    public Payment save(Payment payment) {
        return paymentRepository.save(payment);
    }

    public List<Payment> findAll() {
        return paymentRepository.findAll();
    }
    public List<DailyPaymentDTO> getDailyPayment() {
        return paymentRepository.getDailyPayments();
    }
    public BigDecimal getDailyPayments(LocalDate date) {
        LocalDate startOfDay = date.atStartOfDay().toLocalDate();
        LocalDate endOfDay = date.plusDays(1).atStartOfDay().toLocalDate();

        List<Payment> payments = paymentRepository.findByPaidAtBetween(
                date.atStartOfDay(),
                date.plusDays(1).atStartOfDay()
        );

        return payments.stream()
                .map(Payment::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
    public BigDecimal getMonthlyPayments(YearMonth month) {
        LocalDate start = month.atDay(1);
        LocalDate end = month.atEndOfMonth();

        List<Payment> payments = paymentRepository.findByPaidAtBetween(
                start.atStartOfDay(),
                end.plusDays(1).atStartOfDay()
        );

        return payments.stream()
                .map(Payment::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
    public Optional<Payment> findById(Long id) {
        return paymentRepository.findById(id);
    }

    public Optional<Payment> findByOrderId(Long orderId) {
        return paymentRepository.findByOrderId(orderId);
    }

    public Payment update(Long id, Payment updated) {
        return paymentRepository.findById(id)
                .map(existing -> {
                    existing.setOrder(updated.getOrder());
                    existing.setAmount(updated.getAmount());
                    existing.setMethod(updated.getMethod());
                    existing.setPaidAt(updated.getPaidAt());
                    return paymentRepository.save(existing);
                })
                .orElseThrow(() -> new RuntimeException("Payment not found"));
    }

    public void delete(Long id) {
        paymentRepository.deleteById(id);
    }
}
