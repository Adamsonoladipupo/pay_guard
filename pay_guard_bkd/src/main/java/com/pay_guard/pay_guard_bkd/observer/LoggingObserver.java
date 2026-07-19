package com.pay_guard.pay_guard_bkd.observer;

import com.pay_guard.pay_guard_bkd.data.models.FlaggedTransaction;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;


@Slf4j
@Component
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
