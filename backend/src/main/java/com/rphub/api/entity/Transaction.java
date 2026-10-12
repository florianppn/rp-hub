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
    private BigDecimal amout;

    protected Transaction() {} // pour Hybernate

    public Transaction(
            BankAccount sourceAccount,
            BigDecimal amout,
            CategoryTransaction categoryTransaction,
            TypeTransaction typeTransaction,
            BankAccount targetAccount
    ) {
        this.sourceAccount = sourceAccount;
        this.amout = amout;
        this.categoryTransaction = categoryTransaction;
        this.typeTransaction = typeTransaction;
        this.targetAccount = targetAccount;
    }

    public BankAccount getSourceAccount() {
        return sourceAccount;
    }

    public BigDecimal getAmout() {
        return amout;
    }

    public CategoryTransaction getCategoryTransaction() {
        return categoryTransaction;
    }

    public TypeTransaction getTypeTransaction() {
        return typeTransaction;
    }

    public BankAccount getTargetAccount() {
        return targetAccount;
    }

}