package com.example.bankservice.client.service;

import java.math.BigDecimal;

public interface CurrencyRateService {
    public BigDecimal getUsdKztRate();

    public BigDecimal getUsdRubRate();
}
