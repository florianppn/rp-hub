package com.rphub.api.mapper;

import com.rphub.api.dto.request.BankAccountCreateRequest;
import com.rphub.api.dto.response.BankAccountResponse;
import com.rphub.api.entity.BankAccount;
import com.rphub.api.entity.PlayerCharacter;
import org.springframework.stereotype.Component;

@Component
public class BankAccountMapper {

    public BankAccountResponse toResponse(BankAccount bankAccount) {
        return new BankAccountResponse(
                bankAccount.getAccountNumber(),
                bankAccount.getBalance(),
                bankAccount.getStatus()
        );
    }

    public BankAccount toEntity(
            PlayerCharacter playerCharacter,
            String accountNumber,
            BankAccountCreateRequest request
    ) {
        return new BankAccount(
                playerCharacter,
                accountNumber,
                request.accountBalance()
        );
    }

}
