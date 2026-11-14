package com.rphub.api.dto.request;

import com.rphub.api.enums.GenderCharacter;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.util.UUID;

public record PlayerCharacterCreateRequest(
        @NotBlank UUID userPublicId,
        @NotNull @Size(min=2, max=20) String firstName,
        @NotNull @Size(min=2, max=20) String lastName,
        @NotNull @Past LocalDate dateOfBirth,
        @NotNull GenderCharacter gender,
        @NotNull @Size(min=140, max=220) Integer height
) {}
