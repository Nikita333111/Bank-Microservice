package com.example.bankservice.dto;

import com.example.bankservice.entities.MonthlyLimit;
import com.example.bankservice.entities.Transaction;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransactionExLimitResponseDTO {
    private List<Transaction> limitExceededTransactions;
    private List<MonthlyLimit> limits;
}
