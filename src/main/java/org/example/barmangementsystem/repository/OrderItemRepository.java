package org.example.barmangementsystem.repository;

import org.example.barmangementsystem.dto.*;
import org.example.barmangementsystem.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    // e.g., find all items by order id
    java.util.List<OrderItem> findByOrderId(Long orderId);
    // 📊 Profit per Product
    @Query("""
        SELECT new org.example.barmangementsystem.dto.ProductProfitDTO(
            oi.product.name,
            SUM(oi.unitPrice * oi.quantity),
            SUM(oi.product.costPrice * oi.quantity),
            SUM(oi.unitPrice * oi.quantity) - SUM(oi.product.costPrice * oi.quantity)
        )
        FROM OrderItem oi
        GROUP BY oi.product.name
        ORDER BY oi.product.name
    """)
    List<ProductProfitDTO> getProfitPerProduct();

    // 📦 Profit per Category
    @Query("""
        SELECT new org.example.barmangementsystem.dto.CategoryProfitDTO(
            oi.product.category.name,
            SUM(oi.unitPrice * oi.quantity),
            SUM(oi.product.costPrice * oi.quantity),
            SUM(oi.unitPrice * oi.quantity) - SUM(oi.product.costPrice * oi.quantity)
        )
        FROM OrderItem oi
        GROUP BY oi.product.category.name
        ORDER BY oi.product.category.name
    """)
    List<CategoryProfitDTO> getProfitPerCategory();

    // 🍸 Profit per BarSession (with username)
    // 🍸 Profit per BarSession
    @Query("""
    SELECT new org.example.barmangementsystem.dto.SessionProfitDTO(
        s.id,
        s.openedBy.username,
        SUM(oi.unitPrice * oi.quantity),
        SUM(oi.product.costPrice * oi.quantity),
        SUM(oi.unitPrice * oi.quantity) - SUM(oi.product.costPrice * oi.quantity)
    )
    FROM OrderItem oi
    JOIN oi.order o
    JOIN o.barSession s
    WHERE s IS NOT NULL
    GROUP BY s.id, s.openedBy.username
    ORDER BY s.id
""")
    List<SessionProfitDTO> getProfitPerSession();


    // 📅 Daily Profit Trend
    @Query("""
        SELECT new org.example.barmangementsystem.dto.ProfitTrendDTO(
            FUNCTION('DATE', oi.order.orderTime),
            SUM(oi.unitPrice * oi.quantity),
            SUM(oi.product.costPrice * oi.quantity),
            SUM(oi.unitPrice * oi.quantity) - SUM(oi.product.costPrice * oi.quantity)
        )
        FROM OrderItem oi
        GROUP BY FUNCTION('DATE', oi.order.orderTime)
        ORDER BY FUNCTION('DATE', oi.order.orderTime)
    """)
    List<ProfitTrendDTO> getDailyProfitTrend();

    // 🗓 Weekly Profit Trend
    @Query("""
        SELECT new org.example.barmangementsystem.dto.ProfitTrendDTO(
            FUNCTION('YEARWEEK', oi.order.orderTime),
            SUM(oi.unitPrice * oi.quantity),
            SUM(oi.product.costPrice * oi.quantity),
            SUM(oi.unitPrice * oi.quantity) - SUM(oi.product.costPrice * oi.quantity)
        )
        FROM OrderItem oi
        GROUP BY FUNCTION('YEARWEEK', oi.order.orderTime)
        ORDER BY FUNCTION('YEARWEEK', oi.order.orderTime)
    """)
    List<ProfitTrendDTO> getWeeklyProfitTrend();

    // 📆 Monthly Profit Trend
    @Query("""
        SELECT new org.example.barmangementsystem.dto.ProfitTrendDTO(
            FUNCTION('DATE_FORMAT', oi.order.orderTime, '%Y-%m'),
            SUM(oi.unitPrice * oi.quantity),
            SUM(oi.product.costPrice * oi.quantity),
            SUM(oi.unitPrice * oi.quantity) - SUM(oi.product.costPrice * oi.quantity)
        )
        FROM OrderItem oi
        GROUP BY FUNCTION('DATE_FORMAT', oi.order.orderTime, '%Y-%m')
        ORDER BY FUNCTION('DATE_FORMAT', oi.order.orderTime, '%Y-%m')
    """)
    List<ProfitTrendDTO> getMonthlyProfitTrend();
    @Query("""
        SELECT new org.example.barmangementsystem.dto.SaleSummary(
            p.name,
            c.name,
            SUM(oi.quantity),
            SUM(oi.unitPrice * CAST(oi.quantity AS double))
        )
        FROM OrderItem oi
        JOIN oi.product p
        JOIN p.category c
        JOIN oi.order o
        JOIN o.payment pay
        WHERE pay.paidAt >= :startDate
        GROUP BY p.name, c.name
        ORDER BY SUM(oi.unitPrice * CAST(oi.quantity AS double)) DESC
    """)
    List<SaleSummary> findSalesSummarySince(LocalDateTime startDate);
    List<OrderItem> findByOrder_Customer_Id(Long customerId);
}