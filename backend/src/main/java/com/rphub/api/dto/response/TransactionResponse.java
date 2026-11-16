package com.rphub.api.dto.response;

import com.rphub.api.enums.CategoryTransaction;
import com.rphub.api.enums.TypeTransaction;

import java.math.BigDecimal;

public record TransactionResponse(
        String accountNumberSource,
        String accountNumberTarget,
        BigDecimal amount,
        CategoryTransaction category,
        TypeTransaction type
) {}
