package com.rphub.api.entity;

import com.rphub.api.enums.GenderCharacter;
import com.rphub.api.enums.StatusCharacter;
import java.time.LocalDate;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Column;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Max;
import org.hibernate.annotations.Generated;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import org.hibernate.generator.EventType;

@Entity
@Table(name = "characters")
public class PlayerCharacter extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "first_name", nullable = false, length = 50)
    private String firstName;

    @Column(name = "last_name", nullable= false, length = 50)
    private String lastName;

    @Column(name = "date_of_birth", nullable = false)
    private LocalDate dateOfBirth;

    @Enumerated(EnumType.STRING)
    @Column(name = "gender", nullable = false)
    private GenderCharacter gender;

    @Min(140)
    @Max(220)
    @Column(name = "height", nullable = false)
    private Integer height;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, insertable = false)
    @Generated(event = EventType.INSERT)
    private StatusCharacter status;

    @Column(name = "has_staff_tools", nullable = false, insertable = false)
    @Generated(event = EventType.INSERT)
    private Boolean hasStaffTools;

    protected PlayerCharacter() {} // pour Hybernate

    public PlayerCharacter(User user, String firstName, String lastName, LocalDate dateOfBirth, GenderCharacter gender, Integer height) {
        this.user = user;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.height = height;
    }

    public Boolean getHasStaffTools() {
        return hasStaffTools;
    }

    public StatusCharacter getStatus() {
        return status;
    }

    public Integer getHeight() {
        return height;
    }

    public GenderCharacter getGender() {
        return gender;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public User getUser() {
        return user;
    }

    public void setHasStaffTools(Boolean hasStaffTools) {
        this.hasStaffTools = hasStaffTools;
    }

    public void setStatus(StatusCharacter status) {
        this.status = status;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public void setGender(GenderCharacter gender) {
        this.gender = gender;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setUser(User user) {
        this.user = user;
    }
}