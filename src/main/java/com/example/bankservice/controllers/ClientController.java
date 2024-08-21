package com.example.bankservice.controllers;

import com.example.bankservice.dto.MonthlyLimitRequestDTO;
import com.example.bankservice.dto.TransactionExLimitResponseDTO;
import com.example.bankservice.dto.TransactionRequestDTO;
import com.example.bankservice.entities.MonthlyLimit;
import com.example.bankservice.entities.Transaction;
import com.example.bankservice.services.TransactionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/transactions")
public class ClientController {

    private final TransactionService transactionService;

    // Получаем информацию о каждой расходной операции
    @GetMapping
    public ResponseEntity<List<Transaction>> getAllTransactions(){
        return ResponseEntity.ok(transactionService.getAllTransactions());
    }

    // Получаем  список транзакций превысивших лимит,
    // с указанием суммы лимита, валюты (USD) и даты установления
    @GetMapping("/limit-exceeded")
    public ResponseEntity<TransactionExLimitResponseDTO> getAccExceededLimitTransactions(){
        return ResponseEntity.ok(transactionService.getTransactionsExceededLimit());
    }

    @PostMapping
    public ResponseEntity<Long> createTransaction(@Valid @RequestBody TransactionRequestDTO transactionReq){
        Long id = transactionService.createTransaction(transactionReq);
        return new ResponseEntity<>(id, HttpStatus.CREATED);
    }


    // устанавливаем новый лимит в том месяце если предыдущий исчерпал себя
    @PostMapping("/limits")
    public ResponseEntity<String> setGoodsAndServicesTransactionsLimit(@Valid @RequestBody MonthlyLimitRequestDTO monthLimitReq){
        transactionService.setMonthlyLimit(monthLimitReq);
        return ResponseEntity.ok("limit %f{} on categories 'goods and services' set successfully"
                .formatted(monthLimitReq.getLimitAmount()));
    }

    @GetMapping("/limits")
    public ResponseEntity<List<MonthlyLimit>> getAllLimits() {
        return ResponseEntity.ok(transactionService.getAllLimits());
    }

}



















