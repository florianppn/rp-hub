package com.rphub.api.mapper;

import com.rphub.api.dto.response.TransactionResponse;
import com.rphub.api.entity.Transaction;
import org.springframework.stereotype.Component;

@Component
public class TransactionMapper {

    public TransactionResponse toResponse(Transaction transaction) {
        return new TransactionResponse(
                transaction.getSourceAccount().getAccountNumber(),
                transaction.getTargetAccount().getAccountNumber(),
                transaction.getAmount(),
                transaction.getCategoryTransaction(),
                transaction.getTypeTransaction()
        );
    }

}
