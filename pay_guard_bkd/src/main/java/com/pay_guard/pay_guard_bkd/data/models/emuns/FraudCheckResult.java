package com.pay_guard.pay_guard_bkd.data.models.emuns;

public record FraudCheckResult (
        boolean fraudDetected,
        FraudRule fraudRule,
        Severity severity,
        Integer riskScore,
        String reason
){
}
