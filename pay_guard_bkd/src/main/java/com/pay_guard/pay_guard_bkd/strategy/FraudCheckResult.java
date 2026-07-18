package com.pay_guard.pay_guard_bkd.strategy;

import com.pay_guard.pay_guard_bkd.data.models.emuns.FraudRule;
import com.pay_guard.pay_guard_bkd.data.models.emuns.Severity;

public record FraudCheckResult (
        boolean fraudDetected,
        FraudRule fraudRule,
        Severity severity,
        Integer riskScore,
        String reason
){
}
