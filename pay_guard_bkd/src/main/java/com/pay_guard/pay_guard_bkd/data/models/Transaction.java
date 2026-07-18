package com.pay_guard.pay_guard_bkd.data.models;

import com.pay_guard.pay_guard_bkd.data.models.emuns.TransactionStatus;
import com.pay_guard.pay_guard_bkd.data.models.emuns.TransactionType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;


@Entity
@Table(name = "transactions")
@Getter
@Setter
@NoArgsConstructor
public class Transaction extends BaseEntity{

    @Column(nullable = false, length = 20)
    private String maskedCardNumber;

    @Column(nullable = false, unique = true, length = 100)
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

    @Column(nullable = false)
    private Boolean flagged = false;
}
