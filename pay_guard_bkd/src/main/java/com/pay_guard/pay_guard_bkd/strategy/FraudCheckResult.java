package com.pay_guard.pay_guard_bkd.strategy;

import com.pay_guard.pay_guard_bkd.data.models.emuns.FraudRule;
import com.pay_guard.pay_guard_bkd.data.models.emuns.Severity;

public record FraudCheckResult (
        boolean fraudDetected,
        FraudRule fraudRule,
        Severity severity,
        String reason
){
    public int riskScore() {
        return severity == null ? 0 : severity.getRiskScore();
    }
    public static FraudCheckResult noFraud() {
        return new FraudCheckResult(
                false,
                null,
                null,
                null
        );
    }
    public static FraudCheckResult fraud(
            FraudRule fraudRule,
            Severity severity,
            String reason
    ) {

        return new FraudCheckResult(
                true,
                fraudRule,
                severity,
                reason
        );
    }
}
