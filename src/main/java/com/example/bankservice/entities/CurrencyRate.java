package com.example.bankservice.entities;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "currency_rate")
public class CurrencyRate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "rate_date", nullable = false)
    private LocalDate rateDate;
    @Column(name = "currency_pair", nullable = false)
    private String currency;
    @Column(name = "close_rate", nullable = false)
    private BigDecimal close;
}
