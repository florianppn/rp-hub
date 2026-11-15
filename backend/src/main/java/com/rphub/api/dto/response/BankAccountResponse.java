package com.rphub.api.dto.response;

import com.rphub.api.enums.StatusAccount;

import java.math.BigDecimal;
import java.util.UUID;

public record BankAccountResponse(
    UUID publicId,
    String accountNumber,
    BigDecimal balance,
    StatusAccount status
) {}
