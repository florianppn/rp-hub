package com.rphub.api.dto.request;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.util.UUID;

public record BankAccountCreateRequest(
        @NotNull UUID playerCharacterPublicId,
        @NotNull @Positive @Digits(integer=12, fraction=2) BigDecimal accountBalance
) {}
