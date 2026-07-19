package com.pay_guard.pay_guard_bkd.exception;

public class FlaggedTransactionNotFoundException extends RuntimeException {
    public FlaggedTransactionNotFoundException(String message) {
        super(message);
    }
}
