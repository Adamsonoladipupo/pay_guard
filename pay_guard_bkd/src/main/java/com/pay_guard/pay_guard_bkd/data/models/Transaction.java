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

    public void assignMerchant(Merchant merchant) {
        this.merchant = merchant;
    }

    public void assignMaskedCardNumber(String maskedCardNumber) {
        this.maskedCardNumber = maskedCardNumber;
    }

    public void assignCardHash(String cardHash) {
        this.cardHash = cardHash;
    }

    public void assignAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public void assignCurrency(String currency) {
        this.currency = currency;
    }

    public void assignIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public void assignDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public void assignTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }

    public void updateRiskScore(Integer riskScore) {
        this.riskScore = riskScore;
    }

    public void approve() {
        this.status = TransactionStatus.APPROVED;
        this.flagged = false;
    }

    public void reject() {
        this.status = TransactionStatus.REJECTED;
        this.flagged = false;
    }

    public void flag() {
        this.status = TransactionStatus.FLAGGED;
        this.flagged = true;
    }

    public boolean isFlagged() {
        return Boolean.TRUE.equals(flagged);
    }

    public boolean isApproved() {
        return status == TransactionStatus.APPROVED;
    }

    public boolean isRejected() {
        return status == TransactionStatus.REJECTED;
    }

    public boolean isPending() {
        return status == TransactionStatus.PENDING;
    }

    
}
