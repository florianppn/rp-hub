package com.rphub.api.dto.response;

import com.rphub.api.enums.StatusAccount;
import com.rphub.api.enums.TypeAccount;

import java.math.BigDecimal;
import java.util.UUID;

public record BankAccountResponse(
    String accountNumber,
    BigDecimal balance,
    StatusAccount status,
    TypeAccount type
) {}
