package com.example.bankservice.client.service;

import java.math.BigDecimal;

public interface CurrencyService {
    BigDecimal getExchangeRateWithFallback(String symbol);
}
