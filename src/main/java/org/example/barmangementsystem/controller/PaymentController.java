package org.example.barmangementsystem.controller;


import org.example.barmangementsystem.dto.DailyPaymentDTO;
import org.example.barmangementsystem.entity.Payment;
import org.example.barmangementsystem.services.PaymentService;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

@RestController
@RequestMapping("/api/payments")
@CrossOrigin(origins = "http://localhost:5173,http://192.168.100.3:4567")
public class PaymentController {

    private final PaymentService paymentService;
    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping
    public Payment create(@RequestBody Payment payment) {
        return paymentService.save(payment);
    }

    @GetMapping
    public List<Payment> getAll() {
        return paymentService.findAll();
    }
    @GetMapping("/graph")
    public List<DailyPaymentDTO> getDailyPaymentsGraph() {
        return paymentService.getDailyPayment();
    }
    // Daily payments endpoint
    @GetMapping("/daily")
    public BigDecimal getDailyPayments(@RequestParam(required = false) String date) {
        LocalDate localDate = date != null ? LocalDate.parse(date) : LocalDate.now();
        return paymentService.getDailyPayments(localDate);
    }

    // Monthly payments endpoint
    @GetMapping("/monthly")
    public BigDecimal getMonthlyPayments(@RequestParam(required = false) String month) {
        YearMonth yearMonth = month != null ? YearMonth.parse(month) : YearMonth.now();
        return paymentService.getMonthlyPayments(yearMonth);
    }
    @GetMapping("/{id}")
    public Payment getOne(@PathVariable Long id) {
        return paymentService.findById(id)
                .orElseThrow(() -> new RuntimeException("Payment not found"));
    }

    @GetMapping("/by-order/{orderId}")
    public Payment getByOrder(@PathVariable Long orderId) {
        return paymentService.findByOrderId(orderId)
                .orElseThrow(() -> new RuntimeException("Payment not found for order"));
    }

    @PutMapping("/{id}")
    public Payment update(@PathVariable Long id, @RequestBody Payment payment) {
        return paymentService.update(id, payment);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        paymentService.delete(id);
    }
}
