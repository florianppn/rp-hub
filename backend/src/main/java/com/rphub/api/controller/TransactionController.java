package com.rphub.api.controller;

import com.rphub.api.dto.request.DepositCreateRequest;
import com.rphub.api.dto.request.PaymentCreateRequest;
import com.rphub.api.dto.request.WithdrawalCreateRequest;
import com.rphub.api.dto.response.TransactionResponse;
import com.rphub.api.service.TransactionService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping
    public Page<TransactionResponse> getAll(Pageable pageable) {
        return transactionService.readAll(pageable);
    }

    @GetMapping("/{publicId}")
    public TransactionResponse get(
        @PathVariable UUID publicId
    ) {
        return transactionService.read(publicId);
    }

    @PostMapping("/payment")
    @ResponseStatus(HttpStatus.CREATED)
    public TransactionResponse postPayment(
        @Valid @RequestBody PaymentCreateRequest request
    ) {
        return transactionService.createPayment(request);
    }

    @PostMapping("withdrawal")
    @ResponseStatus(HttpStatus.CREATED)
    public TransactionResponse postWithdrawal(
        @Valid @RequestBody WithdrawalCreateRequest request
    ) {
        return transactionService.createWithdrawal(request);
    }

    @PostMapping("deposit")
    @ResponseStatus(HttpStatus.CREATED)
    public TransactionResponse postDeposit(
        @Valid @RequestBody DepositCreateRequest request
    ) {
        return transactionService.createDeposit(request);
    }

}
