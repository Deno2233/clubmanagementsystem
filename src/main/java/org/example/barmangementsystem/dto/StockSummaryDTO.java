package org.example.barmangementsystem.dto;

public class StockSummaryDTO {
    private String productName;
    private int totalIn;
    private int totalOut;
    private int totalAdjustments;
    private int closingBalance;

    public StockSummaryDTO(String productName, int totalIn, int totalOut, int totalAdjustments, int closingBalance) {
        this.productName = productName;
        this.totalIn = totalIn;
        this.totalOut = totalOut;
        this.totalAdjustments = totalAdjustments;
        this.closingBalance = closingBalance;
    }

    // Getters and Setters
    public String getProductName() { return productName; }
    public void setProductName(String productName) { this.productName = productName; }

    public int getTotalIn() { return totalIn; }
    public void setTotalIn(int totalIn) { this.totalIn = totalIn; }

    public int getTotalOut() { return totalOut; }
    public void setTotalOut(int totalOut) { this.totalOut = totalOut; }

    public int getTotalAdjustments() { return totalAdjustments; }
    public void setTotalAdjustments(int totalAdjustments) { this.totalAdjustments = totalAdjustments; }

    public int getClosingBalance() { return closingBalance; }
    public void setClosingBalance(int closingBalance) { this.closingBalance = closingBalance; }
}
