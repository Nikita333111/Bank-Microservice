package com.example.bankservice.dto;

import com.example.bankservice.enums.Currency;
import jakarta.validation.constraints.*;
import lombok.*;

import javax.money.MonetaryAmount;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransactionRequestDTO {
    @Digits(integer = 10, fraction = 0, message = "The account number must consist of 10 digits")
    private Long accountFrom;
    @Digits(integer = 10, fraction = 0, message = "The account number must consist of 10 digits")
    private Long accountTo;
    @Positive(message = "Amount must be positive")
    private BigDecimal amount;
    @NotNull(message = "specify currency")
    @NotBlank(message = "specify currency")
    @Pattern(regexp = "^(RUB|KZT)$", message = "Available currencies (RUB or KZT)")
    private String currency;
    @NotNull(message = "specify category")
    @NotBlank(message = "specify category")
    @Pattern(regexp = "^(goods|services)$", message = "Available categories (goods or services)")
    private String category;
}
