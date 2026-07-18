package com.pay_guard.pay_guard_bkd.dtos.responses;

import com.pay_guard.pay_guard_bkd.data.models.emuns.UserRole;

import java.util.UUID;

public record AdminResponse(
        UUID id,
        String firstName,
        String lastName,
        String email,
        UserRole role
) {}
