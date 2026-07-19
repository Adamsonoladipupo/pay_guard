package com.pay_guard.pay_guard_bkd.builder;

import com.pay_guard.pay_guard_bkd.data.models.FlaggedTransaction;
import com.pay_guard.pay_guard_bkd.data.models.Transaction;
import com.pay_guard.pay_guard_bkd.strategy.FraudCheckResult;
import org.springframework.stereotype.Component;

@Component
public class FlaggedTransactionBuilder {
    private Transaction transaction;
    private FraudCheckResult result;

    public FlaggedTransactionBuilder transaction(
            Transaction transaction
    ) {
        this.transaction = transaction;
        return this;
    }

    public FlaggedTransactionBuilder result(
            FraudCheckResult result
    ) {
        this.result = result;
        return this;
    }

    public FlaggedTransaction build() {

        FlaggedTransaction flaggedTransaction =
                new FlaggedTransaction();

        flaggedTransaction.setTransaction(transaction);

        flaggedTransaction.setFraudRule(
                result.fraudRule()
        );

        flaggedTransaction.setSeverity(
                result.severity()
        );

        flaggedTransaction.setReason(
                result.reason()
        );

        return flaggedTransaction;
    }

    public FlaggedTransactionBuilder reset() {
        transaction = null;
        result = null;
        return this;
    }
}
