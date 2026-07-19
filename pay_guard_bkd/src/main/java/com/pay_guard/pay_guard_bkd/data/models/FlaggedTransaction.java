package com.pay_guard.pay_guard_bkd.data.models;

import com.pay_guard.pay_guard_bkd.data.models.emuns.FraudRule;
import com.pay_guard.pay_guard_bkd.data.models.emuns.InvestigationStatus;
import com.pay_guard.pay_guard_bkd.data.models.emuns.Severity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "flagged_transactions")
@Getter
@Setter
@NoArgsConstructor
public class FlaggedTransaction extends BaseEntity {

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "transaction_id", nullable = false, unique = true)
    private Transaction transaction;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Severity severity;

    @Column(nullable = false, length = 500)
    private String reason;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private FraudRule fraudRule;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private InvestigationStatus investigationStatus = InvestigationStatus.OPEN;

    @Column(nullable = false)
    private Boolean reviewed = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reviewed_by")
    private Admin reviewedBy;

    private LocalDateTime reviewedAt;

    @Column(length = 500)
    private String reviewComment;



    public void assignTransaction(Transaction transaction) {
        this.transaction = transaction;
    }

    public void assignSeverity(Severity severity) {
        this.severity = severity;
    }

    public void assignReason(String reason) {
        this.reason = reason;
    }

    public void assignFraudRule(FraudRule fraudRule) {
        this.fraudRule = fraudRule;
    }




    public void assignForReview(Admin admin) {
        this.reviewedBy = admin;
    }

    public void startInvestigation() {
        this.investigationStatus = InvestigationStatus.IN_PROGRESS;
    }

    public void closeInvestigation(Admin admin, String comment) {
        this.reviewed = true;
        this.reviewedBy = admin;
        this.reviewedAt = LocalDateTime.now();
        this.reviewComment = comment;
        this.investigationStatus = InvestigationStatus.RESOLVED;
    }

    public void reopenInvestigation() {
        this.reviewed = false;
        this.reviewedBy = null;
        this.reviewedAt = null;
        this.reviewComment = null;
        this.investigationStatus = InvestigationStatus.OPEN;
    }




    public boolean isReviewed() {
        return Boolean.TRUE.equals(reviewed);
    }

    public boolean isOpen() {
        return investigationStatus == InvestigationStatus.OPEN;
    }

    public boolean isInProgress() {
        return investigationStatus == InvestigationStatus.IN_PROGRESS;
    }

    public boolean isResolved() {
        return investigationStatus == InvestigationStatus.RESOLVED;
    }

    public boolean isHighSeverity() {
        return severity == Severity.HIGH;
    }

    public boolean isCriticalSeverity() {
        return severity == Severity.CRITICAL;
    }

    

    public void markReviewed(Admin admin, String comment) {
        closeInvestigation(admin, comment);
    }

}
