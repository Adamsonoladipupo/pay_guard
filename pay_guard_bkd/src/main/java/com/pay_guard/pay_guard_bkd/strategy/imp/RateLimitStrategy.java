package com.pay_guard.pay_guard_bkd.strategy.imp;

import com.pay_guard.pay_guard_bkd.dtos.requests.TransactionRequest;
import com.pay_guard.pay_guard_bkd.strategy.FraudCheckResult;
import com.pay_guard.pay_guard_bkd.strategy.FraudDetectionStrategy;
import org.springframework.stereotype.Component;

@Component
public class RateLimitStrategy implements FraudDetectionStrategy {
    @Override
    public FraudCheckResult check(TransactionRequest request) {
        return null;
    }
}
