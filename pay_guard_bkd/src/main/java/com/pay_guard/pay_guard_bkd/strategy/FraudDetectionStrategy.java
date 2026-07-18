package com.pay_guard.pay_guard_bkd.strategy;

import com.pay_guard.pay_guard_bkd.data.models.Transaction;

public interface FraudDetectionStrategy {
    FraudCheckResult check(Transaction transaction);
}
