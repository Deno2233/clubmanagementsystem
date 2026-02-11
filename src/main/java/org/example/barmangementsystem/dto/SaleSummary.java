package org.example.barmangementsystem.dto;




public class SaleSummary {

    private String productName;
    private String categoryName;
    private Long totalQuantity;
    private Double totalSales;

    // ✅ Constructor must match the query exactly
    public SaleSummary(String productName, String categoryName, Long totalQuantity, Double totalSales) {
        this.productName = productName;
        this.categoryName = categoryName;
        this.totalQuantity = totalQuantity;
        this.totalSales = totalSales;
    }

    // ✅ Getters (required for JSON serialization)
    public String getProductName() {
        return productName;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public Long getTotalQuantity() {
        return totalQuantity;
    }

    public Double getTotalSales() {
        return totalSales;
    }

    // Optional: toString (for logging)
    @Override
    public String toString() {
        return "SaleSummary{" +
                "productName='" + productName + '\'' +
                ", categoryName='" + categoryName + '\'' +
                ", totalQuantity=" + totalQuantity +
                ", totalSales=" + totalSales +
                '}';
    }
}
