package org.example.barmangementsystem.dto;


import java.math.BigDecimal;

public class SessionProfitDTO {
    private Long sessionId;
    private String username;
    private BigDecimal revenue;
    private BigDecimal cost;
    private BigDecimal profit;

    public SessionProfitDTO(Long sessionId, String username, BigDecimal revenue, BigDecimal cost, BigDecimal profit) {
        this.sessionId = sessionId;
        this.username = username;
        this.revenue = revenue;
        this.cost = cost;
        this.profit = profit;
    }

    // Getters and setters
    public Long getSessionId() { return sessionId; }
    public String getUsername() { return username; }
    public BigDecimal getRevenue() { return revenue; }
    public BigDecimal getCost() { return cost; }
    public BigDecimal getProfit() { return profit; }
}

