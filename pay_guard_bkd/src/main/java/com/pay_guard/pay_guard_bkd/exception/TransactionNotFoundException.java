package com.pay_guard.pay_guard_bkd.exception;

public class TransactionNotFoundException extends BusinessException {
    public TransactionNotFoundException(String id) {
        super("Transaction " + id + " not found.");
    }
}
