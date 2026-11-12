package com.rphub.api.dto.response;

import java.time.Instant;
import java.util.UUID;

public record UserResponse(
        UUID publicId,
        String username,
        String emailAddress,
        Instant createdAt,
        Instant updatedAt
) {}
