package com.pay_guard.pay_guard_bkd.data.repositories;

import com.pay_guard.pay_guard_bkd.data.models.Transaction;
import com.pay_guard.pay_guard_bkd.data.models.TransactionStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface TransactionRepository extends JpaRepository <Transaction, UUID> {
    List<Transaction> findByStatus(TransactionStatus status);
    List<Transaction> findByIpAddress(String ipAddress);
    List<Transaction> findByCardHash(String cardHash);
    List<Transaction> findByMerchantMerchantId(String merchantId);
    List<Transaction> findByCreatedAtBetween(LocalDateTime start, LocalDateTime end);
    List<Transaction> findTop10ByCardHashOrderByCreatedAtDesc(String cardHash);
}
