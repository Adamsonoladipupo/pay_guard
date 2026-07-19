package com.pay_guard.pay_guard_bkd.util;

public final class CardMaskUtil {
    private CardMaskUtil() {
    }
    public static String mask(String cardNumber) {

        if (cardNumber == null || cardNumber.length() < 4) {
            return "****";
        }
        return "*".repeat(cardNumber.length() - 4)
                + cardNumber.substring(cardNumber.length() - 4);
    }
}
