package com.pay_guard.pay_guard_bkd.dtos.responses;

import com.pay_guard.pay_guard_bkd.data.models.InvestigationStatus;
import com.pay_guard.pay_guard_bkd.data.models.Severity;

import java.time.LocalDateTime;
import java.util.UUID;

public record FlaggedTransactionResponse(
        UUID id,
        Long transactionId,
        Severity severity,
        String reason,
        String fraudRule,
        InvestigationStatus investigationStatus,
        Boolean reviewed,
        LocalDateTime createdAt
) {}
