package org.example.barmangementsystem.dto;



import java.math.BigDecimal;
import java.time.LocalDateTime;

public class BarSessionDTO {
    private Long id;
    private LocalDateTime openedAt;
    private LocalDateTime closedAt;
    private boolean closed;
    private BigDecimal totalSales;
    private String openedBy; // NEW: username or full name

    public BarSessionDTO(Long id, LocalDateTime openedAt, LocalDateTime closedAt,
                         boolean closed, BigDecimal totalSales, String openedBy) {
        this.id = id;
        this.openedAt = openedAt;
        this.closedAt = closedAt;
        this.closed = closed;
        this.totalSales = totalSales;
        this.openedBy = openedBy;
    }

    public Long getId() { return id; }
    public LocalDateTime getOpenedAt() { return openedAt; }
    public LocalDateTime getClosedAt() { return closedAt; }
    public boolean isClosed() { return closed; }
    public BigDecimal getTotalSales() { return totalSales; }
    public String getOpenedBy() { return openedBy; }
}
