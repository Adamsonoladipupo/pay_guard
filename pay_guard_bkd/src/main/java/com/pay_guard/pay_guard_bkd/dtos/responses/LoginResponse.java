package com.pay_guard.pay_guard_bkd.dtos.responses;

public record LoginResponse(
        String token,
        String type,
        String message
) {}
