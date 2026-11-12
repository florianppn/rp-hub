package com.rphub.api.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserCreateRequest(
        @NotBlank @Size(min=3, max=25) String username,
        @NotBlank @Size(min=5, max=40) String password,
        @NotBlank @Email(regexp=".+@.+\\..+") String emailAddress
) {}
