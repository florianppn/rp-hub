package com.rphub.api.entity;

import com.rphub.api.enums.TypeTransaction;
import com.rphub.api.enums.CategoryTransaction;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Enumerated;
import jakarta.persistence.EnumType;
import jakarta.persistence.Column;
import jakarta.validation.constraints.DecimalMin;
import java.math.BigDecimal;

@Entity
@Table(name = "transactions")
public class Transaction extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "source_account_id", updatable = false)
    private BankAccount sourceAccount;

    @ManyToOne(fetch= FetchType.LAZY, optional = false)
    @JoinColumn(name = "target_account_id", updatable = false)
    private BankAccount targetAccount;

    @Enumerated(EnumType.STRING)
    @Column(name = "type_transaction", nullable = false, length = 10, updatable = false)
    private TypeTransaction typeTransaction;

    @Enumerated(EnumType.STRING)
    @Column(name = "category_transaction", nullable = false, length = 10, updatable = false)
    private CategoryTransaction categoryTransaction;

    @DecimalMin(value = "0.01")
    @Column(name = "amount", nullable = false, precision = 15, scale = 2, updatable = false)
    private BigDecimal amount;

    protected Transaction() {} // pour Hybernate

    public Transaction(
            BankAccount sourceAccount,
            BankAccount targetAccount,
            BigDecimal amount,
            CategoryTransaction categoryTransaction,
            TypeTransaction typeTransaction
    ) {
        this.sourceAccount = sourceAccount;
        this.targetAccount = targetAccount;
        this.amount = amount;
        this.categoryTransaction = categoryTransaction;
        this.typeTransaction = typeTransaction;
    }

    public static Transaction transfer(
            BankAccount source,
            BankAccount target,
            BigDecimal amount,
            CategoryTransaction category
    ) {
        return new Transaction(source, target, amount, category, TypeTransaction.TRANSFER);
    }

    public static Transaction payment(
            BankAccount source,
            BankAccount target,
            BigDecimal amount,
            CategoryTransaction category
    ) {
        return new Transaction(source, target, amount, category, TypeTransaction.PAYMENT);
    }

    public static Transaction withdrawal(
            BankAccount source,
            BigDecimal amount,
            CategoryTransaction category
    ) {
        return new Transaction(source, null, amount, category, TypeTransaction.WITHDRAWAL);
    }

    public static Transaction deposit(
            BankAccount target,
            BigDecimal amount,
            CategoryTransaction category
    ) {
        return new Transaction(null, target, amount, category, TypeTransaction.DEPOSIT);
    }

    public BankAccount getSourceAccount() {
        return sourceAccount;
    }

    public BankAccount getTargetAccount() {
        return targetAccount;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public CategoryTransaction getCategoryTransaction() {
        return categoryTransaction;
    }

    public TypeTransaction getTypeTransaction() {
        return typeTransaction;
    }

}