package com.example.bankservice.service;


import com.example.bankservice.client.service.impl.CurrencyRateServiceImpl;
import com.example.bankservice.dto.TransactionRequestDTO;
import com.example.bankservice.entities.MonthlyLimit;
import com.example.bankservice.entities.Transaction;
import com.example.bankservice.enums.Currency;
import com.example.bankservice.repositories.MonthlyLimitRepository;
import com.example.bankservice.repositories.TransactionRepository;
import com.example.bankservice.services.impl.TransactionServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TransactionServiceImplTest {

    @Mock
    private TransactionRepository transactionRepository;
    @Mock
    private MonthlyLimitRepository limitRepository;
    @Mock
    private CurrencyRateServiceImpl rateService;

    @InjectMocks
    private TransactionServiceImpl transactionService;


    @Test
    void createTransactionWithNotExceededLimit(){
        TransactionRequestDTO requestDTO = TransactionRequestDTO.builder()
                .accountFrom(1234567890L)
                .accountTo(9999999999L)
                .currency("KZT")
                .amount(BigDecimal.valueOf(100))
                .category("goods")
                .build();

        MonthlyLimit monthlyLimit = new MonthlyLimit();
        monthlyLimit.setLimitAmount(BigDecimal.valueOf(150));

        Transaction transaction = Transaction.builder()
                .accountFrom(requestDTO.getAccountFrom())
                .accountTo(requestDTO.getAccountTo())
                .currency(requestDTO.getCurrency())
                .amount(requestDTO.getAmount())
                .category(requestDTO.getCategory())
                .transactionDate(OffsetDateTime.now(ZoneOffset.ofHours(5)))
                .limitExceeded(false)
                .build();

        when(limitRepository.findByLimitAmountGreaterThan(any(BigDecimal.class)))
                .thenReturn(Optional.of(monthlyLimit));
        when(rateService.convertKztToUsd(any(BigDecimal.class)))
                .thenReturn(BigDecimal.valueOf(50));
        when(transactionRepository.save(any(Transaction.class)))
                .thenReturn(transaction);

        // when
        Long transactionId = transactionService.createTransaction(requestDTO);

        // then
        verify(limitRepository, times(1)).save(monthlyLimit);
        verify(transactionRepository, times(1)).save(any(Transaction.class));
        assertEquals(transaction.getId(), transactionId);
        assertEquals(BigDecimal.valueOf(100), monthlyLimit.getLimitAmount());
        assertEquals(false, transaction.getLimitExceeded());
    }
}

























