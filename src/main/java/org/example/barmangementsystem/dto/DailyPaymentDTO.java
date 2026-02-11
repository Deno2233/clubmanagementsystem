package org.example.barmangementsystem.dto;



import java.math.BigDecimal;
import java.time.LocalDate;



import java.math.BigDecimal;
import java.sql.Date;

public class DailyPaymentDTO {
    private Date date;
    private BigDecimal totalAmount;

    public DailyPaymentDTO(Date date, BigDecimal totalAmount) {
        this.date = date;
        this.totalAmount = totalAmount;
    }

    public Date getDate() {
        return date;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }
}

