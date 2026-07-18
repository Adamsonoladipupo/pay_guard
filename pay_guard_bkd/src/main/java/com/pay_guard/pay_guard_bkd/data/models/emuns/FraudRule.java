package com.pay_guard.pay_guard_bkd.data.models.emuns;

public enum FraudRule {
    RATE_LIMIT,
    HIGH_AMOUNT,
    BLACKLISTED_MERCHANT,
    VELOCITY_ATTACK,
    SUSPICIOUS_IP,
    DEVICE_MISMATCH
}
