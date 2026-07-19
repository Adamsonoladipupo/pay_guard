package com.pay_guard.pay_guard_bkd.exception;

import java.util.UUID;

public class TransactionNotFoundException extends BusinessException {
    public TransactionNotFoundException(UUID id) {
        super("Transaction " + id + " not found.");
    }
}
