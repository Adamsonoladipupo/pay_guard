package com.pay_guard.pay_guard_bkd.strategy.imp;

import com.pay_guard.pay_guard_bkd.data.models.emuns.FraudRule;
import com.pay_guard.pay_guard_bkd.data.models.emuns.Severity;
import com.pay_guard.pay_guard_bkd.data.repository.TransactionRepository;
import com.pay_guard.pay_guard_bkd.dtos.requests.TransactionRequest;
import com.pay_guard.pay_guard_bkd.strategy.FraudCheckResult;
import com.pay_guard.pay_guard_bkd.strategy.FraudDetectionStrategy;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class VelocityStrategy implements FraudDetectionStrategy {
    private final TransactionRepository repository;

    public VelocityStrategy(TransactionRepository repository) {
        this.repository = repository;
    }

    @Override
    public FraudCheckResult check(TransactionRequest request) {
        String cardHash = CardHashUtil.hash(request.cardNumber());

        long count = repository.countByCardHashAndCreatedAtAfter(
                cardHash,
                LocalDateTime.now().minusMinutes(10)
        );

        if (count >= 5) {

            return new FraudCheckResult(
                    true,
                    FraudRule.VELOCITY_ATTACK,
                    Severity.HIGH,
                    "Card used too frequently in a short period."
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
