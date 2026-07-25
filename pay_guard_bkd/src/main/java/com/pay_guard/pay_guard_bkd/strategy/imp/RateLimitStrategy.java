package com.pay_guard.pay_guard_bkd.strategy.imp;

import com.pay_guard.pay_guard_bkd.config.FraudProperties;
import com.pay_guard.pay_guard_bkd.data.models.emuns.FraudRule;
import com.pay_guard.pay_guard_bkd.data.models.emuns.Severity;
import com.pay_guard.pay_guard_bkd.dtos.requests.TransactionRequest;
import com.pay_guard.pay_guard_bkd.services.RateLimiterService;
import com.pay_guard.pay_guard_bkd.strategy.FraudCheckResult;
import com.pay_guard.pay_guard_bkd.strategy.FraudDetectionStrategy;
import com.pay_guard.pay_guard_bkd.data.repository.TransactionRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class RateLimitStrategy implements FraudDetectionStrategy {

    private final RateLimiterService rateLimiterService;

    public RateLimitStrategy(RateLimiterService rateLimiterService) {
        this.rateLimiterService = rateLimiterService;
    }

    @Override
    public FraudCheckResult check(TransactionRequest request) {

        boolean allowed =
                rateLimiterService.isAllowed(
                        request.ipAddress()
                );

        if (!allowed) {
            return new FraudCheckResult(
                    true,
                    FraudRule.RATE_LIMIT,
                    Severity.CRITICAL,
                    "Too many requests from the same IP address."
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
