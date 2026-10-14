package com.rphub.api.entity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.rphub.api.AbstractPostgresTest;
import com.rphub.api.enums.GenderCharacter;
import com.rphub.api.enums.StatusAccount;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceException;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.hibernate.exception.DataException;

import java.time.LocalDate;
import java.math.BigDecimal;

public class BankAccountPersistenceTest extends AbstractPostgresTest {

    @Autowired
    private EntityManager em;

    @Test
    @DisplayName("Permet de vérifier que les colonnes générées par la base de données sont correctement remplies lors de la persistance d'un compte bancaire.")
    public void persistFillsDatabaseGeneratedColumns() {
        User user = this.createUser("testuser");
        PlayerCharacter playerCharacter = this.createCharacter(user, "John", "Doe");
        BankAccount bankAccount = this.createBankAccount(playerCharacter, "JDF639KMZ349POF129DKNF53649SQM");

        em.flush();

        assertThat(bankAccount.getBalance()).isEqualByComparingTo("0.00");
        assertThat(bankAccount.getStatus()).isEqualTo(StatusAccount.ACTIVE);
    }

    @Test
    @DisplayName("Permet de vérifier que la persistance d'un compte bancaire avec un numéro de compte déjà existant est rejetée.")
    public void duplicateBankAccountIsRejected() {
        User u1 = this.createUser("testuser1");
        PlayerCharacter p1 = this.createCharacter(u1, "John", "Doe");
        PlayerCharacter p2 = this.createCharacter(u1, "Jane", "Smith");
        this.createBankAccount(p1, "JDF639KMZ349POF129DKNF53649SQM");
        em.flush();
        assertThatThrownBy(() -> {
            this.createBankAccount(p2, "JDF639KMZ349POF129DKNF53649SQM");
            em.flush();
        }).isInstanceOf(PersistenceException.class);
    }

    @Test
    @DisplayName("Permet de vérifier que la colonne balance d'un compte bancaire a la précision et l'échelle attendues.")
    public void balanceHasExpectedPrecisionAndScale() {
        BankAccount bankAccount = this.createBankAccount(
            this.createCharacter(
                this.createUser("testuser"), "John", "Doe")
                ,"JDF639KMZ349POF129DKNF53649SQM");
        em.flush();
        assertThatThrownBy(() -> {
            bankAccount.setBalance(new BigDecimal("123456789012398043.45"));
            em.flush();
        }).isInstanceOf(DataException.class);
    }

    @Test
    @DisplayName("Permet de vérifier que la persistance d'un compte bancaire avec un numéro de compte plus court que 30 caractères est rejetée.")
    public void accountNumberShorterThan30CharactersIsRejected() {
        assertThatThrownBy(() -> {
            this.createBankAccount(
                this.createCharacter(
                        this.createUser("testuser"), "John", "Doe")
                ,"JDF639KMZ349POF129DKNF53649S"); //28 caractères
            em.flush();
        }).isInstanceOf(ConstraintViolationException.class);
    }

    @Test
    @DisplayName("Permet de vérifier que la persistance d'un compte bancaire avec un numéro de compte plus long que 30 caractères est rejetée.")
    public void accountNumberLongerThan30CharactersIsRejected() {
        assertThatThrownBy(() -> {
            this.createBankAccount(
                    this.createCharacter(
                            this.createUser("testuser"), "John", "Doe")
                    ,"JDF639KMZ349POF129DKNF53649S34KZ"); //32 caractères
            em.flush();
        }).isInstanceOf(ConstraintViolationException.class);
    }

    @Test
    @DisplayName("Permet de vérifier que le numéro de compte d'un compte bancaire correspond au pattern attendu (30 caractères alphanumériques en majuscules).")
    public void accountNumberMustMatchExpectedPattern() {
        assertThatThrownBy(() -> {
            this.createBankAccount(
                    this.createCharacter(
                            this.createUser("testuser"), "John", "Doe")
                    ,"JDa639_MZ349POF129D?NF53649.34"); //30 caractères
            em.flush();
        }).isInstanceOf(ConstraintViolationException.class);
    }

    @Test
    @DisplayName("Permet de vérifier que les valeurs d'un compte bancaire rechargé sont les mêmes que celles du compte bancaire initial.")
    public void reloadedBankAccountKeepsSameValues() {
        User user = this.createUser("testuser");
        PlayerCharacter playerCharacter = this.createCharacter(user, "John", "Doe");
        BankAccount bankAccount = this.createBankAccount(playerCharacter, "JDF639KMZ349POF129DKNF53649SQM");
        em.flush();
        em.clear();
        BankAccount reloaded = em.find(BankAccount.class, bankAccount.getId());
        assertThat(bankAccount.getPlayerCharacter().getId()).isEqualTo(reloaded.getPlayerCharacter().getId());
        assertThat(bankAccount.getAccountNumber()).isEqualTo(reloaded.getAccountNumber());
        assertThat(bankAccount.getBalance()).isEqualTo(reloaded.getBalance());
        assertThat(bankAccount.getStatus()).isEqualTo(reloaded.getStatus());
    }

    private BankAccount createBankAccount(PlayerCharacter playerCharacter, String accountNumber) {
        BankAccount bankAccount = new BankAccount(playerCharacter, accountNumber);
        em.persist(bankAccount);
        return bankAccount;
    }

    private User createUser(String username) {
        User user = new User(username, "password");
        em.persist(user);
        return user;
    }

    private PlayerCharacter createCharacter(User user, String firstName, String lastName) {
        PlayerCharacter playerCharacter = new PlayerCharacter(
            user,
            firstName,
            lastName,
            LocalDate.of(1990, 1, 1),
            GenderCharacter.MALE,
            180
        );
        em.persist(playerCharacter);
        return playerCharacter;
    }

}
