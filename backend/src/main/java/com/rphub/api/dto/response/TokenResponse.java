package com.rphub.api.dto.response;

public record TokenResponse(
        String accessToken,
        String tokenType,
        long expiresIn
) {}
