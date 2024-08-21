package com.example.bankservice.repositories;

import com.example.bankservice.entities.CurrencyRate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface CurrencyRateRepository extends JpaRepository<CurrencyRate, Long> {
    Optional<CurrencyRate> findByRateDateAndCurrency(LocalDate rateDate, String currencyPair);
}
