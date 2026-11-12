package com.rphub.api.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record EmailUpdateRequest(
        @NotBlank @Email(regexp=".+@.+\\..+") String newEmailAddress
) {}
