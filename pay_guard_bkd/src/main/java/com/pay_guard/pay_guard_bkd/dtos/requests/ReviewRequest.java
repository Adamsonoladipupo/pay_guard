package com.pay_guard.pay_guard_bkd.dtos.requests;

import com.pay_guard.pay_guard_bkd.data.models.emuns.InvestigationStatus;

public record ReviewRequest(
        InvestigationStatus investigationStatus,
        String reviewComment
) {
}
