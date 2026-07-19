package com.pay_guard.pay_guard_bkd.observer;

import com.pay_guard.pay_guard_bkd.data.models.FlaggedTransaction;

public interface TransactionObserver {
    void update(FlaggedTransaction flaggedTransaction);

}
