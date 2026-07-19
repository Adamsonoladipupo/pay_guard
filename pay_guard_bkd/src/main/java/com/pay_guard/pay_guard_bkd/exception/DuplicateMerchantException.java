package com.pay_guard.pay_guard_bkd.exception;

public class DuplicateMerchantException extends BusinessException {
    public DuplicateMerchantException(String merchantID) {
        super("Merchant already exists: " + merchantId);
    }
}
