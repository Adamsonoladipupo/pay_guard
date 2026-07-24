package com.pay_guard.pay_guard_bkd.dtos.responses;

import com.pay_guard.pay_guard_bkd.data.models.emuns.InvestigationStatus;
import com.pay_guard.pay_guard_bkd.data.models.emuns.Severity;

import java.time.LocalDateTime;
import java.util.UUID;

public record FlaggedTransactionResponse(
        UUID id,
        UUID transactionId,
        Severity severity,
        String reason,
        String fraudRule,
        InvestigationStatus investigationStatus,
        Boolean reviewed,
        LocalDateTime createdAt
) {}
