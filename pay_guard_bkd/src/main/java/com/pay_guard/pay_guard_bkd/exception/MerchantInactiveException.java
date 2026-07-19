package com.pay_guard.pay_guard_bkd.exception;

public class MerchantInactiveException extends BusinessException {
    public MerchantInactiveException(String merchantId) {
        super("Merchant " + merchantId + " is not active.");
    }
}
