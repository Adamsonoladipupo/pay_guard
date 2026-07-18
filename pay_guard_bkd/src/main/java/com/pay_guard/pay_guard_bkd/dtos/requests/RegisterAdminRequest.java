package com.pay_guard.pay_guard_bkd.dtos.requests;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegisterAdminRequest(
        @NotBlank
        String firstName,

        @NotBlank
        String lastName,

        @Email
        String email,

        @Size(min = 8)
        String password
) {}
