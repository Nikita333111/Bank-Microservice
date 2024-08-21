package com.example.bankservice.client.service.impl;

import com.example.bankservice.entities.CurrencyRate;
import com.example.bankservice.repositories.CurrencyRateRepository;
import com.example.bankservice.client.service.CurrencyRateService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CurrencyRateServiceImpl implements CurrencyRateService {

    private final CurrencyServiceImpl currencyService;
    private final CurrencyRateRepository currencyRepository;
    private final String USDKZT = "USD/KZT";
    private final String USDRUB = "USD/RUB";


    public BigDecimal convertKztToUsd(BigDecimal kztAmount){
        return kztAmount.divide(getUsdKztRate(), RoundingMode.DOWN);
    }

    public BigDecimal convertRubToUsd(BigDecimal rubAmount){
        return rubAmount.divide(getUsdRubRate(), RoundingMode.DOWN);
    }

    @Override
    public BigDecimal getUsdKztRate() {
        return getRate(USDKZT);
    }

    @Override
    public BigDecimal getUsdRubRate() {
        return getRate(USDRUB);
    }

    private BigDecimal getRate(String symbol) {
        Optional<CurrencyRate> existedCurrencyRate = currencyRepository.findByRateDateAndCurrency(LocalDate.now(), symbol);

        if (existedCurrencyRate.isPresent() && existedCurrencyRate.get().getClose() != null){
            return existedCurrencyRate.get().getClose();
        }

        BigDecimal closeRate = currencyService.getExchangeRateWithFallback(symbol);
        CurrencyRate currencyRate = CurrencyRate.builder()
                .rateDate(LocalDate.now())
                .currency(symbol)
                .close(closeRate)
                .build();
        currencyRepository.save(currencyRate);

        return closeRate;
    }


}
