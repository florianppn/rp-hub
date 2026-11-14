package com.rphub.api.dto.response;

import com.rphub.api.enums.GenderCharacter;
import com.rphub.api.enums.StatusCharacter;

import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

public record PlayerCharacterResponse(
        UUID publicId,
        String firstName,
        String lastName,
        LocalDate dateOfBirth,
        GenderCharacter gender,
        Integer height,
        StatusCharacter status,
        Instant createdAt,
        Instant updatedAt
) {}
