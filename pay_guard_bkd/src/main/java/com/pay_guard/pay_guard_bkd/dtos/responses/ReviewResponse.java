package com.pay_guard.pay_guard_bkd.dtos.responses;

import com.pay_guard.pay_guard_bkd.data.models.emuns.InvestigationStatus;

import java.util.UUID;

public record ReviewResponse(
        UUID flaggedTransactionId,
        InvestigationStatus investigationStatus,
        String reviewComment,
        String message
) {
}
