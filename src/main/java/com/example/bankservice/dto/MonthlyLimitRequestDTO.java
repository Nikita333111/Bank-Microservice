package com.example.bankservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MonthlyLimitRequestDTO {
    @Positive(message = "Limit sum must be positive")
    private BigDecimal limitAmount;
    @NotNull(message = "specify currency")
    @NotBlank(message = "specify currency")
    @Pattern(regexp = "^(USD)$", message = "You can set the amount of the transaction limit only in USD")
    private String currencyShortname;
}
