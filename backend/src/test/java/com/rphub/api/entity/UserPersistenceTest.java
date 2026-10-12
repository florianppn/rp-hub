package com.rphub.api.entity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.rphub.api.AbstractPostgresTest;
import com.rphub.api.enums.Role;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class UserPersistenceTest extends AbstractPostgresTest {

    @Autowired
    private EntityManager em;

    @Test
    void persistFillsDatabaseGeneratedColumns() {
        User user = new User("toto", "motdepasse");

        em.persist(user);
        em.flush();

        assertThat(user.getId()).isNotNull();
        assertThat(user.getPublicId()).isNotNull();
        assertThat(user.getCreatedAt()).isNotNull();
        assertThat(user.getRole()).isEqualTo(Role.PLAYER);
    }

    @Test
    void duplicateUsernameIsRejected() {
        em.persist(new User("toto", "motdepasse"));
        em.flush();

        assertThatThrownBy(() -> {
            em.persist(new User("toto", "motdepasse"));
            em.flush();
        }).isInstanceOf(PersistenceException.class);
    }

    @Test
    void reloadedUserKeepsSameValues() {
        User user = new User ("toto", "motdepasse");
        em.persist(user);
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

}
