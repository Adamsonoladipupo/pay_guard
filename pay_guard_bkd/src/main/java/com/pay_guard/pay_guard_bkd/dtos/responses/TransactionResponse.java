package com.pay_guard.pay_guard_bkd.dtos.responses;

import com.pay_guard.pay_guard_bkd.data.models.emuns.TransactionStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record TransactionResponse(
        UUID id,
        String maskedCardNumber,
        BigDecimal amount,
        String currency,
        String merchantId,
        Integer riskScore,
        TransactionStatus status,
        LocalDateTime createdAt
) {}
