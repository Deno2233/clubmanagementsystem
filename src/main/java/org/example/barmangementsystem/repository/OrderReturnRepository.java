package org.example.barmangementsystem.repository;


import org.example.barmangementsystem.entity.OrderReturn;
import org.example.barmangementsystem.entity.RefundPayment;
import org.example.barmangementsystem.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.List;

public interface OrderReturnRepository extends JpaRepository<OrderReturn, Long> {

    // total refunded per day
    @Query("SELECT SUM(r.amount) FROM RefundPayment r WHERE DATE(r.refundedAt) = CURRENT_DATE")
    BigDecimal findTodayTotalRefunds();

    // refunds by user
    List<RefundPayment> findByProcessedBy(User user);

}
