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
@Table(name = "transaction")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "account_from", nullable = false)
    private Long accountFrom;
    @Column(name = "account_to", nullable = false)
    private Long accountTo;
    @Column(name = "currency_shortname", nullable = false)
    private String currency;
    @Column(name = "amount", nullable = false)
    private BigDecimal amount;
    @Column(name = "category", nullable = false)
    private String category;
    @Column(name = "transaction_date", nullable = false)
    private OffsetDateTime transactionDate;
    @Column(name = "limit_exceeded", nullable = false)
    private Boolean limitExceeded;
}
