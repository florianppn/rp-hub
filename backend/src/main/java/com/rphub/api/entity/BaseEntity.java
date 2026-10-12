package com.rphub.api.entity;

import jakarta.persistence.Column;
import jakarta.persistence.GenerationType;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import org.hibernate.annotations.Generated;
import org.hibernate.generator.EventType;
import org.hibernate.annotations.UpdateTimestamp;
import java.time.Instant;
import java.util.UUID;

@MappedSuperclass
public abstract class BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @Column(name="public_id", insertable = false, updatable = false)
    @Generated(event = EventType.INSERT)
    protected UUID publicId;

    @Column(name="created_at", insertable = false, updatable = false)
    @Generated(event = EventType.INSERT)
    protected Instant createdAt;

    @UpdateTimestamp
    @Column(name="updated_at", insertable = false, updatable = false)
    protected Instant updatedAt;

    public Long getId() {
        return id;
    }

    public UUID getPublicId() {
        return publicId;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

}