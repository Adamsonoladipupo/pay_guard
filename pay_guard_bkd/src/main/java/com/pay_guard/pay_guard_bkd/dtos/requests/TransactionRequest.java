package com.pay_guard.pay_guard_bkd.dtos.requests;

import com.pay_guard.pay_guard_bkd.data.models.TransactionType;

import java.math.BigDecimal;

public record TransactionRequest(
        @NotBlank(message = "Card number is required")
        String cardNumber,

        @NotNull(message = "Amount is required")
        @Positive
        BigDecimal amount,

        @NotBlank(message = "Merchant ID is required")
        String merchantId,

        @NotBlank(message = "IP Address is required")
        String ipAddress,

        @NotBlank(message = "Currency is required")
        String currency,

        TransactionType transactionType,

        String deviceId
) {}
