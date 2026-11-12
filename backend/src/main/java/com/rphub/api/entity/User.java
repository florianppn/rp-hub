package com.rphub.api.entity;

import com.rphub.api.enums.Role;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import org.hibernate.annotations.Generated;
import org.hibernate.generator.EventType;
import java.time.Instant;

@Entity
@Table(name = "users")
public class User extends BaseEntity {

    @Column(name = "username", unique = true, nullable = false, length = 25)
    private String username;

    @Column(name = "password_hash", nullable = false, length = 255)
    private String passwordHash;

    @Column(name = "email_address", unique=true, nullable=false)
    private String emailAddress;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", insertable = false)
    @Generated(event = EventType.INSERT)
    private Role role;

    @Column(name = "deleted_at")
    private Instant deleted_at;

    protected User() {} // pour Hybernate

    public User(String username, String passwordHash, String emailAddress) {
        this.username = username;
        this.passwordHash = passwordHash;
        this.emailAddress = emailAddress;
    }

    public Instant getDeletedAt() {
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

    public String getEmailAddress() {
        return emailAddress;
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

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

}