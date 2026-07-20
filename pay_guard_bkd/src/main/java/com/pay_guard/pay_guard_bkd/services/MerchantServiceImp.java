package com.pay_guard.pay_guard_bkd.services;

import com.pay_guard.pay_guard_bkd.data.models.Merchant;
import com.pay_guard.pay_guard_bkd.data.models.emuns.MerchantStatus;
import com.pay_guard.pay_guard_bkd.data.repository.MerchantRepository;
import com.pay_guard.pay_guard_bkd.exception.MerchantInactiveException;
import com.pay_guard.pay_guard_bkd.exception.MerchantNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MerchantServiceImp {
    private final MerchantRepository repository;

    public MerchantServiceImp(MerchantRepository repository) {
        this.repository = repository;
    }
    public Merchant validateAndGetMerchant(String merchantId) {

        Merchant merchant = repository.findByMerchantId(merchantId)
                .orElseThrow(() ->
                        new MerchantNotFoundException(merchantId));

        if (merchant.getStatus() != MerchantStatus.ACTIVE) {
            throw new MerchantInactiveException(merchantId);
        }
        return merchant;
    }
}
