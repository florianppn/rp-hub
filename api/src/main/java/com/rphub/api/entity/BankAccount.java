package com.rphub.api.entity;

import com.rphub.api.enums.StatusAccount;
import jakarta.validation.constraints.DecimalMin;
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

    @DecimalMin(value = "0.00")
    @Column(name = "balance", nullable = false, precision = 15, scale = 2, insertable = false)
    @Generated(event = EventType.INSERT)
    private BigDecimal balance;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 10, insertable = false)
    @Generated(event = EventType.INSERT)
    private StatusAccount status;

    private BankAccount() {} // pour Hybernate

    public BankAccount(PlayerCharacter playerCharacter, String accountNumber) {
        this.playerCharacter = playerCharacter;
        this.accountNumber = accountNumber;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public PlayerCharacter getPlayerCharacter() {
        return playerCharacter;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public StatusAccount getStatus() {
        return status;
    }

    public void setStatus(StatusAccount status) {
        this.status = status;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

}