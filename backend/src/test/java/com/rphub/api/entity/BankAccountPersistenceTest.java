package com.rphub.api.entity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.rphub.api.AbstractPostgresTest;
import com.rphub.api.enums.GenderCharacter;
import com.rphub.api.enums.StatusAccount;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;

public class BankAccountPersistenceTest extends AbstractPostgresTest {

    @Autowired
    private EntityManager em;

    @Test
    void persistFillsDatabaseGeneratedColumns() {
        User user = new User("toto", "password");
        PlayerCharacter playerCharacter = new PlayerCharacter(
                user,
                "John",
                "Doe",
                LocalDate.of(1990, 1, 1),
                GenderCharacter.MALE,
                180
        );
        BankAccount bankAccount = new BankAccount(playerCharacter, "1234567890");

        em.persist(user);
        em.persist(playerCharacter);
        em.persist(bankAccount);
        em.flush();

        assertThat(bankAccount.getBalance()).isEqualByComparingTo("0.00");
        assertThat(bankAccount.getStatus()).isEqualTo(StatusAccount.ACTIVE);
    }



}
