package com.rphub.api.entity;

import com.rphub.api.enums.Role;
import jakarta.persitence.Entity;

@Entity
@Table(name = "users")
public class User extends BaseEntity {

    @Column(name = "username", unique = true, nullable = false, length = 25)
    private String username;
    @Column(name = "password_hash", nullable = false, length = 255)
    private String passwordHash;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", insertable = false)
    @Generated(event = EventType.INSERT)
    private Role role;

    @Column(name = "deleted_at", updatable = true)
    private Instant deleted_at;

    private User() {} // pour Hybernate

    public User(Sring username, String passwordHash) {
        this.username = username;
        this.passwordHash = passwordHash;
    }

    public Instant getDeleted_at() {
        return deleted_at;
    }

    public Role getRole() {
        return role;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public String getUsername() {
        return username;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public void setUsername(String username) {
        this.username = username;
    }

}