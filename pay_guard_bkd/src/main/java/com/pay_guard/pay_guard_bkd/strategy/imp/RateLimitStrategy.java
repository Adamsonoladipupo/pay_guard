package com.pay_guard.pay_guard_bkd.strategy.imp;

import com.pay_guard.pay_guard_bkd.data.models.emuns.FraudRule;
import com.pay_guard.pay_guard_bkd.data.models.emuns.Severity;
import com.pay_guard.pay_guard_bkd.dtos.requests.TransactionRequest;
import com.pay_guard.pay_guard_bkd.strategy.FraudCheckResult;
import com.pay_guard.pay_guard_bkd.strategy.FraudDetectionStrategy;
import com.pay_guard.pay_guard_bkd.data.repository.TransactionRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class RateLimitStrategy implements FraudDetectionStrategy {
    private final TransactionRepository repository;

    public RateLimitStrategy(TransactionRepository repository) {
        this.repository = repository;
    }

    @Override
    public FraudCheckResult check(TransactionRequest request) {
        long count = repository.countByIpAddressAndCreatedAtAfter(
                request.ipAddress(),
                LocalDateTime.now().minusMinutes(1)
        );

        if (count > 5) {

            return new FraudCheckResult(
                    true,
                    FraudRule.RATE_LIMIT,
                    Severity.CRITICAL,
                    "More than five requests detected from the same IP."
            );
        }

        return new FraudCheckResult(
                false,
                null,
                null,
                null
        );
    }
}
