package com.pay_guard.pay_guard_bkd.dtos.responses;

import com.pay_guard.pay_guard_bkd.data.models.UserRole;

public record AdminResponse(
        Long id,

        String firstName,

        String lastName,

        String email,

        UserRole role
) {}
