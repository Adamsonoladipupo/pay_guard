package com.pay_guard.pay_guard_bkd.data.models.emuns;

import com.pay_guard.pay_guard_bkd.data.models.FraudRule;
import com.pay_guard.pay_guard_bkd.data.models.Severity;

public enum FraudCheckResult {
    boolean fraudDetected,
    FraudRule fraudRule,
    Severity severity,
    Integer riskScore,
    String reason
}
