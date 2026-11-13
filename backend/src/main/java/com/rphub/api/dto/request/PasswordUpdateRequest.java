package com.rphub.api.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record PasswordUpdateRequest(
        @NotBlank @Size(min=5, max=40) String password,
        @NotBlank @Size(min=5, max=40) String newPassword
) {}
