package com.pay_guard.pay_guard_bkd.observer;

import com.pay_guard.pay_guard_bkd.data.models.FlaggedTransaction;

public class LoggingObserver implements TransactionObserver{
    @Override
    public void update(FlaggedTransaction flaggedTransaction) {
        log.info(
                "Transaction [{}] flagged. Severity: {}",
                flaggedTransaction.getTransaction().getId(),
                flaggedTransaction.getSeverity()
        );
    }
}
