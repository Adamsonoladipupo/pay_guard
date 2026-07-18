package com.pay_guard.pay_guard_bkd.dtos.responses;

public record DashboardResponse(
        long totalTransactions,
        long approvedTransactions,
        long flaggedTransactions,
        long declinedTransactions,
        long totalMerchants,
        long totalAdmins
) {}
