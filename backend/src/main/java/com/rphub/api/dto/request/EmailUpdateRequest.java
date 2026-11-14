package com.rphub.api.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record EmailUpdateRequest(
        @NotBlank @Size(min=5, max=40) String password,
        @NotBlank @Email(regexp = "^[A-Za-z0-9._+-]+@[A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*\\.[A-Za-z]{2,}$") String newEmailAddress
) {}
