package com.pay_guard.pay_guard_bkd.data.repository;

import com.pay_guard.pay_guard_bkd.data.models.FlaggedTransaction;
import com.pay_guard.pay_guard_bkd.data.models.emuns.InvestigationStatus;
import com.pay_guard.pay_guard_bkd.data.models.emuns.Severity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface FlaggedTransactionRepository extends JpaRepository <FlaggedTransaction, UUID> {
    List<FlaggedTransaction> findBySeverity(Severity severity);
    List<FlaggedTransaction> findByInvestigationStatus(InvestigationStatus status);
    List<FlaggedTransaction> findByReviewedFalse();
}
