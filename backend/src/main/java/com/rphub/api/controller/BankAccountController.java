package com.rphub.api.controller;

import com.rphub.api.dto.request.BankAccountCreateRequest;
import com.rphub.api.dto.response.BankAccountResponse;
import com.rphub.api.service.BankAccountService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/bank-accounts")
public class BankAccountController {

    private final BankAccountService bankAccountService;

    public BankAccountController(BankAccountService bankAccountService) {
        this.bankAccountService = bankAccountService;
    }

    @GetMapping
    public Page<BankAccountResponse> getAll(Pageable pageable) {
        return bankAccountService.readAll(pageable);
    }

    @GetMapping("{publicId}")
    public BankAccountResponse get(
        @PathVariable UUID publicId
    ) {
        return bankAccountService.read(publicId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BankAccountResponse post(
        @Valid @RequestBody BankAccountCreateRequest request
    ) {
        return bankAccountService.create(request);
    }

    @DeleteMapping("/{publicId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(
        @PathVariable UUID publicId
    ) {
        bankAccountService.delete(publicId);
    }

}
