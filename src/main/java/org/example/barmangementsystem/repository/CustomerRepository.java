package org.example.barmangementsystem.repository;

import org.example.barmangementsystem.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    // Optional custom queries, e.g.
    boolean existsByPhone(String phone);
}
