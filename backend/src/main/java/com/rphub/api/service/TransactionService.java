package com.rphub.api.service;

import com.rphub.api.dto.request.DepositCreateRequest;
import com.rphub.api.dto.request.PaymentCreateRequest;
import com.rphub.api.dto.request.WithdrawalCreateRequest;
import com.rphub.api.dto.response.TransactionResponse;
import com.rphub.api.entity.BankAccount;
import com.rphub.api.entity.Transaction;
import com.rphub.api.exception.ResourceNotFoundException;
import com.rphub.api.mapper.TransactionMapper;
import com.rphub.api.repository.BankAccountRepository;
import com.rphub.api.repository.TransactionRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final BankAccountRepository bankAccountRepository;
    private final TransactionMapper transactionMapper;

    public TransactionService(
            TransactionRepository transactionRepository,
            BankAccountRepository bankAccountRepository,
            TransactionMapper transactionMapper
    ) {
        this.transactionRepository = transactionRepository;
        this.bankAccountRepository = bankAccountRepository;
        this.transactionMapper = transactionMapper;
    }

    @Transactional(readOnly = true)
    public Page<TransactionResponse> readAll(Pageable pageable) {
        return transactionRepository.findAll(pageable).map(transactionMapper::toResponse);
    }

    @Transactional(readOnly = true)
    public TransactionResponse read(UUID publicId) {
        Transaction tx = transactionRepository.findByPublicId(publicId)
                .orElseThrow(() -> new ResourceNotFoundException("Transaction non trouvé avec l'ID : " + publicId));
        return transactionMapper.toResponse(tx);
    }

    @Transactional
    public TransactionResponse createPayment(PaymentCreateRequest request) {
        BankAccount source = loadAccount(request.accountNumberSource());
        BankAccount target = loadAccount(request.accountNumberTarget());
        Transaction tx = Transaction.payment(source, target, request.amount(), request.category());
        source.debit(request.amount());
        target.credit(request.amount());
        return transactionMapper.toResponse(transactionRepository.save(tx));
    }

    @Transactional
    public TransactionResponse createWithdrawal(WithdrawalCreateRequest request) {
        BankAccount source = loadAccount(request.accountNumberSource());
        Transaction tx = Transaction.withdrawal(source, request.amount(), request.category());
        source.debit(request.amount());
        return transactionMapper.toResponse(transactionRepository.save(tx));
    }

    @Transactional
    public TransactionResponse createDeposit(DepositCreateRequest request) {
        BankAccount target = loadAccount(request.accountNumberTarget());
        Transaction tx = Transaction.deposit(target, request.amount(), request.category());
        target.credit(request.amount());
        return transactionMapper.toResponse(transactionRepository.save(tx));
    }

    private BankAccount loadAccount(String accountNumber) {
        return bankAccountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Compte bancaire non trouvé avec le numéro : " + accountNumber)
                );
    }

}
