package com.pay_guard.pay_guard_bkd.services;

import com.pay_guard.pay_guard_bkd.data.models.FlaggedTransaction;

public interface NotificationService {
    void notifyObservers(
            FlaggedTransaction flaggedTransaction
    );
}
