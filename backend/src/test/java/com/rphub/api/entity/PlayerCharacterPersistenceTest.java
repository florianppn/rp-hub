package com.rphub.api.entity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.rphub.api.AbstractPostgresTest;
import com.rphub.api.enums.GenderCharacter;
import com.rphub.api.enums.StatusCharacter;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceException;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import java.time.LocalDate;

public class PlayerCharacterPersistenceTest extends AbstractPostgresTest {

    @Autowired
    private EntityManager em;

    @Test
    @DisplayName("Permet de vérifier que les colonnes générées par la base de données sont correctement remplies lors de la persistance d'un personnage joueur.")
    public void persistFillsDatabaseGeneratedColumns() {
        User user = this.createUser("toto");
        PlayerCharacter playerCharacter = this.createCharacter(user, "John", "Doe");
        em.flush();
        assertThat(playerCharacter.getStatus()).isEqualTo(StatusCharacter.ACTIVE);
        assertThat(playerCharacter.getHasStaffTools()).isFalse();
    }

    @Test
    @DisplayName("Permet de vérifier que la persistance d'un personnage joueur avec un prénom et un nom de famille déjà existants est rejetée.")
    public void persistWithDuplicateFirstNameAndLastNameIsRejected() {
        User user1 = this.createUser("user1");
        User user2 = this.createUser("user2");
        this.createCharacter(user1, "John", "Doe");
        em.flush();
        assertThatThrownBy(() -> {
            this.createCharacter(user2, "John", "Doe");
            em.flush();
        }).isInstanceOf(PersistenceException.class);
    }

    @Test
    @DisplayName("Permet de vérifier que la persistance d'un personnage joueur avec une taille inférieure à 140 cm est rejetée.")
    public void heightCannotBeLessThan140() {
        User user = this.createUser("user1");
        PlayerCharacter playerCharacter = this.createCharacter(user, "John", "Doe");
        em.flush();
        assertThatThrownBy(() -> {
            playerCharacter.setHeight(120);
            em.flush();
        }).isInstanceOf(ConstraintViolationException.class);
    }

    @Test
    @DisplayName("Permet de vérifier que la persistance d'un personnage joueur avec une taille supérieure à 220 cm est rejetée.")
    public void heightCannotBeGreaterThan220() {
        User user = this.createUser("user1");
        PlayerCharacter playerCharacter = this.createCharacter(user, "John", "Doe");
        em.flush();
        assertThatThrownBy(() -> {
            playerCharacter.setHeight(240);
            em.flush();
        }).isInstanceOf(ConstraintViolationException.class);
    }

    @Test
    @DisplayName("Permet de vérifier que la persistance d'un personnage joueur avec une date de naissance dans le futur est rejetée.")
    public void dateOfBirthCannotBeInTheFuture() {
        User user = this.createUser("user1");
        PlayerCharacter playerCharacter = this.createCharacter(user, "John", "Doe");
        em.flush();
        assertThatThrownBy(() -> {
            playerCharacter.setDateOfBirth(LocalDate.now().plusDays(5));
            em.flush();
        }).isInstanceOf(ConstraintViolationException.class);
    }

    @Test
    @DisplayName("Permet de vérifier que les valeurs d'un personnage joueur rechargé sont les mêmes que celles de l'utilisateur initial.")
    public void reloadedPlayerCharacterKeepsSameValues() {
        User user = this.createUser("user1");
        PlayerCharacter playerCharacter = this.createCharacter(user, "John", "Doe");
        em.flush();
        em.clear();
        PlayerCharacter reloaded = em.find(PlayerCharacter.class, playerCharacter.getId());
        assertThat(reloaded.getFirstName()).isEqualTo(playerCharacter.getFirstName());
        assertThat(reloaded.getLastName()).isEqualTo(playerCharacter.getLastName());
        assertThat(reloaded.getDateOfBirth()).isEqualTo(playerCharacter.getDateOfBirth());
        assertThat(reloaded.getGender()).isEqualTo(playerCharacter.getGender());
        assertThat(reloaded.getHeight()).isEqualTo(playerCharacter.getHeight());
        assertThat(reloaded.getStatus()).isEqualTo(playerCharacter.getStatus());
        assertThat(reloaded.getHasStaffTools()).isEqualTo(playerCharacter.getHasStaffTools());
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