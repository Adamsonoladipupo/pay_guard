package com.pay_guard.pay_guard_bkd.listener;

import com.pay_guard.pay_guard_bkd.event.TransactionProcessedEvent;
import org.springframework.context.event.EventListener;

public class AuditEventListener {
    @EventListener
    public void onTransactionProcessed(
            TransactionProcessedEvent event
    ) {

        System.out.println(
                "Audit Log : "
                        + event.getTransaction().getId()
        );

    }
}
