package org.example.barmangementsystem.dto;



import java.math.BigDecimal;
import java.math.RoundingMode;

public class CategoryProfitDTO {
    private String categoryName;
    private BigDecimal revenue;
    private BigDecimal cost;
    private BigDecimal profit;

    public CategoryProfitDTO(String categoryName, BigDecimal revenue, BigDecimal cost, BigDecimal profit) {
        this.categoryName = categoryName;
        this.revenue = revenue;
        this.cost = cost;
        this.profit = profit;
    }

    public BigDecimal getMarginPercent() {
        if (revenue == null || revenue.compareTo(BigDecimal.ZERO) == 0) return BigDecimal.ZERO;
        return profit.multiply(BigDecimal.valueOf(100))
                .divide(revenue, 2, RoundingMode.HALF_UP);
    }

    public String getCategoryName() { return categoryName; }
    public BigDecimal getRevenue() { return revenue; }
    public BigDecimal getCost() { return cost; }
    public BigDecimal getProfit() { return profit; }
}
