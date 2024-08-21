package com.example.bankservice.client.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class CurrencyResponseDTO {
    @JsonProperty("values")
    private List<CurrencyValue> values;

    @Data
    public static class CurrencyValue{
        @JsonProperty("datetime")
        private String datetime;
        @JsonProperty("close")
        private BigDecimal close;

    }
}



















