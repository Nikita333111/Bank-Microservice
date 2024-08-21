package com.example.bankservice.services.impl;

import com.example.bankservice.client.service.impl.CurrencyRateServiceImpl;
import com.example.bankservice.dto.MonthlyLimitRequestDTO;
import com.example.bankservice.dto.TransactionExLimitResponseDTO;
import com.example.bankservice.dto.TransactionRequestDTO;
import com.example.bankservice.entities.MonthlyLimit;
import com.example.bankservice.entities.Transaction;
import com.example.bankservice.enums.Currency;
import com.example.bankservice.exception.LimitAlreadyExist;
import com.example.bankservice.repositories.MonthlyLimitRepository;
import com.example.bankservice.repositories.TransactionRepository;
import com.example.bankservice.services.TransactionService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final MonthlyLimitRepository limitRepository;
    private final CurrencyRateServiceImpl rateService;
    @Override
    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }

    @Transactional
    @Override
    public TransactionExLimitResponseDTO getTransactionsExceededLimit() {
        List<Transaction> limitExceededTransactions = transactionRepository.findAllByLimitExceededTrue();
        List<MonthlyLimit> limits = limitRepository.findAll();


        TransactionExLimitResponseDTO exLimitResponseDTO = TransactionExLimitResponseDTO.builder()
                .limitExceededTransactions(limitExceededTransactions)
                .limits(limits)
                .build();

        return exLimitResponseDTO;
    }

    @Transactional
    @Override
    public Long createTransaction(TransactionRequestDTO transactionReq) {
        Transaction transaction = Transaction.builder()
                .accountFrom(transactionReq.getAccountFrom())
                .accountTo(transactionReq.getAccountTo())
                .currency(transactionReq.getCurrency())
                .amount(transactionReq.getAmount())
                .category(transactionReq.getCategory())
                .transactionDate(OffsetDateTime.now(ZoneOffset.ofHours(5)))
                .build();

        // Лимит создается по дефолту при запуске
        // Мы не можем создать новый лимит пока не законился старый
        // Это позволяет запрашивать лимит по наличию средств
        Optional<MonthlyLimit> optionalLimit = limitRepository.findByLimitAmountGreaterThan(BigDecimal.valueOf(0));
        if(optionalLimit.isPresent())
        {
            MonthlyLimit limit = optionalLimit.get();
            BigDecimal usdTransactionAmount;
            // конфертируем валюту транкзакции в usd для сравнения с лимитом
            if (transactionReq.getCurrency().equals("KZT"))
                usdTransactionAmount = rateService.convertKztToUsd(transactionReq.getAmount());
            else
                usdTransactionAmount = rateService.convertRubToUsd(transactionReq.getAmount());
            // сравниваем и устанавливаем новый лимит или обнуляем лимит и помечаем транзакцию limit_exceeded
            if(limit.getLimitAmount().compareTo(usdTransactionAmount) >= 0){
                BigDecimal newAmount = limit.getLimitAmount().subtract(usdTransactionAmount);
                limit.setLimitAmount(newAmount);
                transaction.setLimitExceeded(false);
            } else {
                limit.setLimitAmount(BigDecimal.valueOf(0));
                transaction.setLimitExceeded(true);
            }
            limitRepository.save(limit);
        }
        else
        {
            transaction.setLimitExceeded(true);
        }

        return transactionRepository.save(transaction).getId();
    }

    @Override
    public void setMonthlyLimit(MonthlyLimitRequestDTO monthLimitReq) {
        List<MonthlyLimit> existingLimits = limitRepository.findAll();

        // Если неисчерпанный лимит существует выбрасываем ошибку
        for (MonthlyLimit limit: existingLimits){
            if (limit.getLimitAmount().doubleValue() > 0.0)
                throw new LimitAlreadyExist("Limit for this month is not exceeded yet.");
        }

        MonthlyLimit monthlyLimit = MonthlyLimit.builder()
                .limitAmount(monthLimitReq.getLimitAmount())
                .startLimit(monthLimitReq.getLimitAmount())
                .limitDate(OffsetDateTime.now(ZoneOffset.ofHours(5)))
                .currencyShortname(monthLimitReq.getCurrencyShortname())
                .build();
        limitRepository.save(monthlyLimit);
    }

    @Override
    public List<MonthlyLimit> getAllLimits() {
        return limitRepository.findAll();
    }


    // Дефолтный месячный лимит взять за $1000, который сбрасывается первого числа каждый месяц
    @Scheduled(cron = "0 0 0 1 * *")
    @Transactional
    public void resetMonthlyLimit() {
        limitRepository.deleteAll();
        MonthlyLimit monthlyLimit = MonthlyLimit.builder()
                .limitAmount(new BigDecimal("1000.00"))
                .startLimit(new BigDecimal("1000.00"))
                .limitDate(OffsetDateTime.now(ZoneOffset.ofHours(5)))
                .currencyShortname("USD")
                .build();
        limitRepository.save(monthlyLimit);
    }
}
























