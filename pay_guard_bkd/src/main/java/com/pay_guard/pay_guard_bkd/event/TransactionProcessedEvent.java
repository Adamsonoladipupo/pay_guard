package com.pay_guard.pay_guard_bkd.event;

import com.pay_guard.pay_guard_bkd.data.models.Transaction;

public class TransactionProcessedEvent {
    private final Transaction transaction;

    public TransactionProcessedEvent(Transaction transaction) {
        this.transaction = transaction;
    }

    public Transaction getTransaction() {
        return transaction;
    }
}
