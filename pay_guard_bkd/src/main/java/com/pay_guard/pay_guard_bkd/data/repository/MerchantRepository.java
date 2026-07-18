package com.pay_guard.pay_guard_bkd.data.repository;

import com.pay_guard.pay_guard_bkd.data.models.Merchant;
import com.pay_guard.pay_guard_bkd.data.models.emuns.MerchantStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface MerchantRepository extends JpaRepository <Merchant, UUID>{
    Optional<Merchant> findByMerchantId(String merchantId);
    boolean existsByMerchantId(String merchantId);
    List<Merchant> findByStatus(MerchantStatus status);

}
