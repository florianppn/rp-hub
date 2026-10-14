package com.rphub.api.entity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.rphub.api.AbstractPostgresTest;
import com.rphub.api.enums.Role;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class UserPersistenceTest extends AbstractPostgresTest {

    @Autowired
    private EntityManager em;

    @Test
    @DisplayName("Permet de vérifier que les colonnes générées par la base de données sont correctement remplies lors de la persistance d'un utilisateur.")
    public void persistFillsDatabaseGeneratedColumns() {
        User user = this.createUser("toto");
        em.flush();

        assertThat(user.getId()).isNotNull();
        assertThat(user.getPublicId()).isNotNull();
        assertThat(user.getCreatedAt()).isNotNull();
        assertThat(user.getRole()).isEqualTo(Role.PLAYER);
    }

    @Test
    @DisplayName("Permet de vérifier que la persistance d'un utilisateur avec un nom d'utilisateur déjà existant est rejetée.")
    public void duplicateUsernameIsRejected() {
        this.createUser("toto");
        em.flush();

        assertThatThrownBy(() -> {
            this.createUser("toto");
            em.flush();
        }).isInstanceOf(PersistenceException.class);
    }

    @Test
    @DisplayName("Permet de vérifier que la persistance d'un utilisateur avec un nom) d'utilisateur dépassant la longueur maximale est rejetée.")
    public void usernameExceedingMaximumLengthIsRejected() {
        assertThatThrownBy(() -> {
            this.createUser("tototototototototototototo"); // 26 caractères, dépasse la limite de 25
            em.flush();
        }).isInstanceOf(PersistenceException.class);
    }

    @Test
    @DisplayName("Permet de vérifier que les valeurs d'un utilisateur rechargé sont les mêmes que celles de l'utilisateur initial.")
    public void reloadedUserKeepsSameValues() {
        User user = this.createUser("toto");
        em.flush();
        em.clear();

        User reloaded = em.find(User.class, user.getId());
        assertThat(reloaded.getId()).isEqualTo(user.getId());
        assertThat(reloaded.getPublicId()).isEqualTo(user.getPublicId());
        assertThat(reloaded.getCreatedAt()).isEqualTo(user.getCreatedAt());
        assertThat(reloaded.getRole()).isEqualTo(user.getRole());
        assertThat(reloaded.getUsername()).isEqualTo(user.getUsername());
        assertThat(reloaded.getPasswordHash()).isEqualTo(user.getPasswordHash());
    }

    private User createUser(String username) {
        User user = new User(username, "password");
        em.persist(user);
        return user;
    }

}
