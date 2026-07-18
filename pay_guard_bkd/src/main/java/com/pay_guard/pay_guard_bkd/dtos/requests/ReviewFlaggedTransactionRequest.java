package com.pay_guard.pay_guard_bkd.dtos.requests;

import com.pay_guard.pay_guard_bkd.data.models.InvestigationStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ReviewFlaggedTransactionRequest(
        @NotNull
        InvestigationStatus investigationStatus,

        @NotBlank
        String reviewComment
) {}
