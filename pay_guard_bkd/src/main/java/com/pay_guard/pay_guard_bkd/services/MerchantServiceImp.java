package com.pay_guard.pay_guard_bkd.services;

import com.pay_guard.pay_guard_bkd.data.models.Merchant;
import com.pay_guard.pay_guard_bkd.data.models.emuns.MerchantStatus;
import com.pay_guard.pay_guard_bkd.data.repository.MerchantRepository;
import com.pay_guard.pay_guard_bkd.dtos.requests.MerchantRequest;
import com.pay_guard.pay_guard_bkd.dtos.responses.MerchantResponse;
import com.pay_guard.pay_guard_bkd.exception.MerchantInactiveException;
import com.pay_guard.pay_guard_bkd.exception.MerchantNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class MerchantServiceImp implements MerchantService{
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

    @Override
    public MerchantResponse createMerchant(MerchantRequest request) {
        return null;
    }

    @Override
    public MerchantResponse getMerchant(UUID merchantId) {
        return null;
    }

    @Override
    public List<MerchantResponse> getMerchants() {
        return List.of();
    }
}
