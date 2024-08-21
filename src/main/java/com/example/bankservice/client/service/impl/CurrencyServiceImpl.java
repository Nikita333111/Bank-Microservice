package com.example.bankservice.client.service.impl;

import com.example.bankservice.client.config.TwelvedataApiProperties;
import com.example.bankservice.client.dto.CurrencyResponseDTO;
import com.example.bankservice.client.exception.CurrencyDataUnavailable;
import com.example.bankservice.client.service.CurrencyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.math.BigDecimal;
import java.net.URI;

@Service
@RequiredArgsConstructor
public class CurrencyServiceImpl implements CurrencyService {

    private final RestTemplate restTemplate;
    private final TwelvedataApiProperties apiProperties;

    public BigDecimal getExchangeRate(String symbol) {
        URI uri = UriComponentsBuilder.fromHttpUrl(apiProperties.getBaseUrl())
                .queryParam("symbol", symbol)
                .queryParam("interval", "1day")
                .queryParam("apikey", apiProperties.getKey())
                .queryParam("outputsize", "2")
                .build()
                .toUri();
        CurrencyResponseDTO response = restTemplate.getForObject(uri, CurrencyResponseDTO.class);

        if(response != null && response.getValues() != null && !response.getValues().isEmpty()){
            return response.getValues().get(0).getClose();
        }

        throw new CurrencyDataUnavailable("Failed to retrieve exchange rate for " + symbol);
    }

    public BigDecimal getExchangeRateWithFallback(String symbol) {
        try {
            return getExchangeRate(symbol);
        } catch (RuntimeException e) {
            System.out.println("Current data unavailable, using previous close");
            return getPreviousClose(symbol);
        }
    }

    private BigDecimal getPreviousClose(String symbol){
        URI uri = UriComponentsBuilder.fromHttpUrl(apiProperties.getBaseUrl())
                .queryParam("symbol", symbol)
                .queryParam("interval", "1day")
                .queryParam("apikey", apiProperties.getKey())
                .queryParam("outputsize", "2")
                .build()
                .toUri();

        CurrencyResponseDTO response = restTemplate.getForObject(uri, CurrencyResponseDTO.class);

        if (response != null && response.getValues() != null && response.getValues().size() > 1) {
            return response.getValues().get(1).getClose();
        }

        throw new CurrencyDataUnavailable("Failed to retrieve previous close for " + symbol);
    }

}

















