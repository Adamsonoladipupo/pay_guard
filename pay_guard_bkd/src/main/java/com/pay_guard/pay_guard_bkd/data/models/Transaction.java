package com.pay_guard.pay_guard_bkd.data.models;

import jakarta.persistence.*;

import java.math.BigDecimal;

public class Transaction extends BaseEntity{

    @Column(nullable = false, length = 20)
    private String maskedCardNumber;

    @Column(nullable = false, length = 20)
    private String cardHash;

    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal amount;

    @Column(nullable = false, length = 10)
    private String currency;

    @Column(nullable = false, length = 45)
    private String ipAddress;

    @Column(length = 100)
    private String deviceId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TransactionType transactionType;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TransactionStatus status = TransactionStatus.PENDING;

    @Column(nullable = false)
    private Integer riskScore = 0;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "merchant_id", nullable = false)
    private Merchant merchant;
}
