package com.pay_guard.pay_guard_bkd.strategy.imp;

import com.pay_guard.pay_guard_bkd.config.FraudProperties;
import com.pay_guard.pay_guard_bkd.data.models.emuns.FraudRule;
import com.pay_guard.pay_guard_bkd.data.models.emuns.Severity;
import com.pay_guard.pay_guard_bkd.data.repository.TransactionRepository;
import com.pay_guard.pay_guard_bkd.dtos.requests.TransactionRequest;
import com.pay_guard.pay_guard_bkd.strategy.FraudCheckResult;
import com.pay_guard.pay_guard_bkd.strategy.FraudDetectionStrategy;
import com.pay_guard.pay_guard_bkd.util.CardHashUtil;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class VelocityStrategy implements FraudDetectionStrategy {
    private final TransactionRepository repository;
    private final FraudProperties properties;

    public VelocityStrategy(TransactionRepository repository, FraudProperties properties) {
        this.repository = repository;
        this.properties = properties;
    }

    @Override
    public FraudCheckResult check(TransactionRequest request) {
        String cardHash = CardHashUtil.hash(request.cardNumber());

        long count = repository.countByCardHashAndCreatedAtAfter(
                cardHash,
                LocalDateTime.now().minusMinutes(
                        properties.getVelocity().getWindowMinutes()
                )
        );

        if (count >= properties.getVelocity().getMaxTransactions()) {

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
