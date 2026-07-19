package com.pay_guard.pay_guard_bkd.dtos.responses;

public record DashboardSummaryResponse(
        long totalTransactions,
        long totalFlaggedTransactions,
        long totalApprovedTransactions,
        long totalRejectedTransactions,
        long totalMerchants,
        long totalAdmins
) {
}
