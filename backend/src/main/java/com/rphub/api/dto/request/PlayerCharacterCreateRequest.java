package com.rphub.api.dto.request;

import com.rphub.api.enums.GenderCharacter;
import jakarta.validation.constraints.*;

import java.time.LocalDate;
import java.util.UUID;

public record PlayerCharacterCreateRequest(
        @NotNull UUID userPublicId,
        @NotBlank @Size(min=2, max=20) String firstName,
        @NotBlank @Size(min=2, max=20) String lastName,
        @NotNull @Past LocalDate dateOfBirth,
        @NotNull GenderCharacter gender,
        @NotNull @Min(140) @Max(220) Integer height
) {}
