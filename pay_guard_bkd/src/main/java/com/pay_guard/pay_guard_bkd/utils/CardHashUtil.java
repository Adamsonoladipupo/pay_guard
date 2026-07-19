package com.pay_guard.pay_guard_bkd.utils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public final class CardHashUtil {
    private CardHashUtil() {
    }

    public static String hash(String cardNumber) {

        try {

            MessageDigest digest =
                    MessageDigest.getInstance("SHA-256");

            byte[] hash =
                    digest.digest(
                            cardNumber.getBytes(StandardCharsets.UTF_8)
                    );

            StringBuilder builder = new StringBuilder();

            for (byte b : hash) {

                builder.append(
                        String.format("%02x", b)
                );
            }

            return builder.toString();

        } catch (NoSuchAlgorithmException e) {

            throw new IllegalStateException(
                    "SHA-256 algorithm not found.",
                    e
            );
        }
    }
}
