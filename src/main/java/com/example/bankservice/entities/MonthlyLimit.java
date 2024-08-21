package com.example.bankservice.entities;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "monthly_limit")
public class MonthlyLimit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "limit_amount", nullable = false)
    private BigDecimal limitAmount;
    @Column(name = "start_limit", nullable = false)
    private BigDecimal startLimit;
    @Column(name = "limit_datetime", nullable = false)
    private OffsetDateTime limitDate;
    @Column(name = "limit_currency_shortname", nullable = false)
    private String currencyShortname;
}
