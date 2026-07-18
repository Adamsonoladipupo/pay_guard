package com.pay_guard.pay_guard_bkd.dtos.responses;

import com.pay_guard.pay_guard_bkd.data.models.TransactionStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record TransactionResponse(
        Long id,
        String maskedCardNumber,
        BigDecimal amount,
        String currency,
        String merchantId,
        Integer riskScore,
        TransactionStatus status,
        LocalDateTime createdAt
) {}
