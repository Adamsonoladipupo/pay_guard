package com.pay_guard.pay_guard_bkd.exception;

public class MerchantNotFoundException extends BusinessException {
    public MerchantNotFoundException(String merchantId) {
        super("Merchant with ID " + merchantId + " was not found.");
    }
}
