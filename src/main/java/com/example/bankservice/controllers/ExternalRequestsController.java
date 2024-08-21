package com.example.bankservice.controllers;

import com.example.bankservice.client.service.CurrencyService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/currency")
public class ExternalRequestsController {

    private final CurrencyService currencyService;


}
