package com.pay_guard.pay_guard_bkd.strategy.imp;

import com.pay_guard.pay_guard_bkd.config.FraudProperties;
import com.pay_guard.pay_guard_bkd.data.models.emuns.FraudRule;
import com.pay_guard.pay_guard_bkd.data.models.emuns.Severity;
import com.pay_guard.pay_guard_bkd.dtos.requests.TransactionRequest;
import com.pay_guard.pay_guard_bkd.strategy.FraudCheckResult;
import com.pay_guard.pay_guard_bkd.strategy.FraudDetectionStrategy;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class HighAmountStrategy implements FraudDetectionStrategy {

    private final FraudProperties properties;

    public HighAmountStrategy(FraudProperties properties) {
        this.properties = properties;
    }

//    private static final BigDecimal LIMIT = new BigDecimal("500000");

    @Override
    public FraudCheckResult check(TransactionRequest request) {
        if (request.amount().compareTo(properties.getHighAmount().getLimit()) > 0) {

            return new FraudCheckResult(
                    true,
                    FraudRule.HIGH_AMOUNT,
                    Severity.HIGH,
                    "Transaction amount exceeds ₦500,000."
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
