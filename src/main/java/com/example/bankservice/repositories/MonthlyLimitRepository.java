package com.example.bankservice.repositories;

import com.example.bankservice.entities.MonthlyLimit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Optional;

@Repository
public interface MonthlyLimitRepository extends JpaRepository<MonthlyLimit, Long> {
    Optional<MonthlyLimit> findByLimitAmountGreaterThan(BigDecimal sum);
}
