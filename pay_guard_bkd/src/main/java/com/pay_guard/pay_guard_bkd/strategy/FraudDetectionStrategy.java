package com.pay_guard.pay_guard_bkd.strategy;

import com.pay_guard.pay_guard_bkd.data.models.Transaction;
import com.pay_guard.pay_guard_bkd.dtos.requests.TransactionRequest;

public interface FraudDetectionStrategy {
//    FraudCheckResult check(Transaction transaction);
    FraudCheckResult check(TransactionRequest request);
}

