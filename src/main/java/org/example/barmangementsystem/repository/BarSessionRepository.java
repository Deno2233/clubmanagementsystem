package org.example.barmangementsystem.repository;


import org.example.barmangementsystem.entity.BarSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface BarSessionRepository extends JpaRepository<BarSession, Long> {
    Optional<BarSession> findByClosedFalse();
    // Optionally: find sessions by user
    @Query("SELECT s FROM BarSession s WHERE s.openedBy.username = :username ORDER BY s.openingTime DESC")
    java.util.List<BarSession> findByUser(String username);
    List<BarSession> findByClosedTrueOrderByClosingTimeDesc();
}
