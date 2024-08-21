package com.example.bankservice.services;

import com.example.bankservice.dto.MonthlyLimitRequestDTO;
import com.example.bankservice.dto.TransactionExLimitResponseDTO;
import com.example.bankservice.dto.TransactionRequestDTO;
import com.example.bankservice.entities.MonthlyLimit;
import com.example.bankservice.entities.Transaction;

import java.util.List;

public interface TransactionService {
    TransactionExLimitResponseDTO getTransactionsExceededLimit();

    List<Transaction> getAllTransactions();

    Long createTransaction(TransactionRequestDTO transactionReq);

    void setMonthlyLimit(MonthlyLimitRequestDTO monthLimitReq);

    List<MonthlyLimit> getAllLimits();
}
