package com.rphub.api.dto.request;

import com.rphub.api.enums.CategoryTransaction;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record WithdrawalCreateRequest(
        @NotBlank String accountNumberSource,
        @NotNull @Positive @Digits(integer=13, fraction=2) BigDecimal amount,
        @NotNull CategoryTransaction category
) {}
