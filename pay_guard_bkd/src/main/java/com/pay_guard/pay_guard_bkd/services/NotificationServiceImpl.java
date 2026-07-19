package com.pay_guard.pay_guard_bkd.services;

import com.pay_guard.pay_guard_bkd.data.models.FlaggedTransaction;
import com.pay_guard.pay_guard_bkd.observer.TransactionObserver;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService{
    private final List<TransactionObserver> observers;

    @Override
    public void notifyObservers(
            FlaggedTransaction flaggedTransaction
    ) {

        observers.forEach(observer ->
                observer.update(flaggedTransaction)
        );

    }
}
