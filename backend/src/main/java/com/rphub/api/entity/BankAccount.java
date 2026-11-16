package com.rphub.api.entity;

import com.rphub.api.enums.StatusAccount;
import com.rphub.api.exception.BankingException;
import jakarta.persistence.OneToOne;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import org.hibernate.annotations.Generated;
import org.hibernate.generator.EventType;
import java.math.BigDecimal;

@Entity
@Table(name = "bank_accounts")
public class BankAccount extends BaseEntity {

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "character_id", nullable = false, updatable = false)
    private PlayerCharacter playerCharacter;

    @Column(name="account_number", unique = true, nullable = false, length = 30, updatable = false)
    private String accountNumber;

    @Column(name = "account_balance", nullable = false, precision = 15, scale = 2, insertable = false)
    @Generated(event = EventType.INSERT)
    private BigDecimal accountBalance;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 10, insertable = false)
    @Generated(event = EventType.INSERT)
    private StatusAccount status;

    protected BankAccount() {} // pour Hybernate

    public BankAccount(PlayerCharacter playerCharacter, String accountNumber) {
        this.playerCharacter = playerCharacter;
        this.accountNumber = accountNumber;
    }

    public BankAccount(PlayerCharacter playerCharacter, String accountNumber, BigDecimal accountBalance) {
        this.playerCharacter = playerCharacter;
        this.accountNumber = accountNumber;
        this.accountBalance = accountBalance;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public PlayerCharacter getPlayerCharacter() {
        return playerCharacter;
    }

    public BigDecimal getBalance() {
        return accountBalance;
    }

    public StatusAccount getStatus() {
        return status;
    }

    public void setStatus(StatusAccount status) {
        this.status = status;
    }

    public void setAccountBalance(BigDecimal accountBalance) {
        this.accountBalance = accountBalance;
    }

    public void debit(BigDecimal amount) {
        if(accountBalance.compareTo(amount) >= 0) {
            accountBalance = accountBalance.subtract(amount);
        } else {
            throw new BankingException("solde insuffisant");
        }
    }

    public void credit(BigDecimal amount) {
        if(amount.compareTo(BigDecimal.ZERO) > 0) {
            accountBalance = accountBalance.add(amount);
        } else {
            throw new BankingException("montant invalide");
        }
    }

}