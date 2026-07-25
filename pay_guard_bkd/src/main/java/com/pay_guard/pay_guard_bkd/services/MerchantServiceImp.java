package com.pay_guard.pay_guard_bkd.services;

import com.pay_guard.pay_guard_bkd.annotation.Audit;
import com.pay_guard.pay_guard_bkd.data.models.Merchant;
import com.pay_guard.pay_guard_bkd.data.models.emuns.MerchantCategory;
import com.pay_guard.pay_guard_bkd.data.models.emuns.MerchantStatus;
import com.pay_guard.pay_guard_bkd.data.repository.MerchantRepository;
import com.pay_guard.pay_guard_bkd.dtos.requests.MerchantRequest;
import com.pay_guard.pay_guard_bkd.dtos.responses.MerchantResponse;
import com.pay_guard.pay_guard_bkd.exception.MerchantInactiveException;
import com.pay_guard.pay_guard_bkd.exception.MerchantNotFoundException;
import com.pay_guard.pay_guard_bkd.mappers.MerchantMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class MerchantServiceImp implements MerchantService{
    private final MerchantRepository repository;
    private final MerchantMapper merchantMapper;

    public MerchantServiceImp(MerchantRepository repository, MerchantMapper merchantMapper) {
        this.repository = repository;
        this.merchantMapper = merchantMapper;
    }

    @Override
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
    @Audit("Merchant Created")
    public MerchantResponse createMerchant(MerchantRequest request) {
        Merchant merchant = merchantMapper.toEntity(request);
        merchant.setMerchantId(generateMerchantId());
        if (merchant.getMerchantCategory() == null) {
            merchant.setMerchantCategory(MerchantCategory.OTHER);
        }
        Merchant savedMerchant = repository.save(merchant);

        return merchantMapper.toResponse(savedMerchant);
    }

    @Override
    public MerchantResponse getMerchant(UUID merchantId) {
        Merchant merchant = repository.findById(merchantId)
                .orElseThrow(() ->
                        new MerchantNotFoundException(
                                merchantId.toString()
                        ));
        return merchantMapper.toResponse(merchant);
    }

    @Override
    public List<MerchantResponse> getMerchants() {
        return repository.findAll()
                .stream()
                .map(merchantMapper::toResponse)
                .toList();
    }

    private String generateMerchantId() {
        String merchantId;
        do {
            merchantId = "PG-MER-"
                    + UUID.randomUUID()
                    .toString()
                    .substring(0, 8)
                    .toUpperCase();

        } while (repository.existsByMerchantId(merchantId));
        return merchantId;
    }
}
