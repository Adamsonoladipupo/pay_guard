package com.pay_guard.pay_guard_bkd.exception;

public class DuplicateMerchantException extends BusinessException {
    public DuplicateMerchantException(String merchantId) {
        super("Merchant already exists: " + merchantId);
    }
}
