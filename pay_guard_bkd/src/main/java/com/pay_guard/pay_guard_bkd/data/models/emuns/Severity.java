package com.pay_guard.pay_guard_bkd.data.models.emuns;

public enum Severity {
    LOW(10),
    MEDIUM(30),
    HIGH(60),
    CRITICAL(100);

    private final int riskScore;
    Severity(int riskScore) {
        this.riskScore = riskScore;
    }
    public int getRiskScore() {
        return riskScore;
    }
}
