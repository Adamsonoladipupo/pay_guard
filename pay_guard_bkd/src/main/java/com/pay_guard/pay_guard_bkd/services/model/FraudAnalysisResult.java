package com.pay_guard.pay_guard_bkd.services.model;

import com.pay_guard.pay_guard_bkd.data.models.emuns.TransactionStatus;
import com.pay_guard.pay_guard_bkd.strategy.FraudCheckResult;

import java.util.List;

public record FraudAnalysisResult(
        boolean fraudDetected,
        int totalRiskScore,
        TransactionStatus recommendedStatus,
        List<FraudCheckResult> triggeredRules
) {
}
