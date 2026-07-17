package com.pay_guard.pay_guard_bkd.data.models;

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
}
