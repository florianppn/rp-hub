package com.rphub.api.service;

import com.rphub.api.dto.request.BankAccountCreateRequest;
import com.rphub.api.dto.response.BankAccountResponse;
import com.rphub.api.entity.BankAccount;
import com.rphub.api.entity.PlayerCharacter;
import com.rphub.api.exception.ConflictException;
import com.rphub.api.exception.ResourceNotFoundException;
import com.rphub.api.mapper.BankAccountMapper;
import com.rphub.api.repository.BankAccountRepository;
import com.rphub.api.repository.PlayerCharacterRepository;
import com.rphub.api.service.generator.AccountNumberGenerator;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class BankAccountService {

    private final BankAccountRepository bankAccountRepository;
    private final PlayerCharacterRepository playerCharacterRepository;
    private final BankAccountMapper bankAccountMapper;
    private final AccountNumberGenerator accountNumberGenerator;

    public BankAccountService(
            BankAccountRepository bankAccountRepository,
            PlayerCharacterRepository playerCharacterRepository,
            BankAccountMapper bankAccountMapper,
            AccountNumberGenerator accountNumberGenerator
    ) {
        this.bankAccountRepository = bankAccountRepository;
        this.playerCharacterRepository = playerCharacterRepository;
        this.bankAccountMapper = bankAccountMapper;
        this.accountNumberGenerator = accountNumberGenerator;
    }

    @Transactional(readOnly = true)
    public Page<BankAccountResponse> readAll(Pageable pageable) {
        return bankAccountRepository.findAll(pageable).map(bankAccountMapper::toResponse);
    }

    @Transactional(readOnly = true)
    public BankAccountResponse read(UUID publicId) {
        BankAccount bankAccount = bankAccountRepository.findByPublicId(publicId)
                .orElseThrow(() -> new ResourceNotFoundException("Compte bancaire non trouvé avec l'ID : " + publicId));
        return bankAccountMapper.toResponse(bankAccount);
    }

    @Transactional
    public BankAccountResponse create(BankAccountCreateRequest request) {
        PlayerCharacter playerCharacter = playerCharacterRepository.findByPublicId(request.playerCharacterPublicId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Personnage non trouvé avec l'ID : " +
                        request.playerCharacterPublicId()
                ));
        if(bankAccountRepository.existsByPlayerCharacter(playerCharacter)) {
            throw new ConflictException(
                    "Un compte bancaire est déjà associé à " +
                    playerCharacter.getFirstName() + " " +
                    playerCharacter.getLastName()
            );
        }
        String accountNumber = accountNumberGenerator.generate("MB");
        BankAccount bankAccount = bankAccountMapper.toEntity(playerCharacter, accountNumber, request);
        return bankAccountMapper.toResponse(bankAccountRepository.save(bankAccount));
    }

    @Transactional
    public void delete(UUID publicId) {
        if(bankAccountRepository.deleteByPublicId(publicId) == 0) {
            throw new ConflictException("Erreur lors de la suppression du compte bancaire avec l'ID : " + publicId);
        }
    }

}
