package com.rphub.api.dto.request;

import com.rphub.api.enums.TypeAccount;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.util.UUID;

public record BankAccountCreateRequest(
        @NotNull UUID playerCharacterPublicId,
        @NotNull @Positive @Digits(integer=13, fraction=2) BigDecimal accountBalance,
        @NotBlank TypeAccount type
) {}
