package com.pay_guard.pay_guard_bkd.strategy.imp;

import com.pay_guard.pay_guard_bkd.dtos.requests.TransactionRequest;
import com.pay_guard.pay_guard_bkd.strategy.FraudCheckResult;
import com.pay_guard.pay_guard_bkd.strategy.FraudDetectionStrategy;

public class MerchantStrategy implements FraudDetectionStrategy {

    private final MerchantRepository merchantRepository;

    public MerchantStrategy(MerchantRepository merchantRepository) {
        this.merchantRepository = merchantRepository;
    }

    @Override
    public FraudCheckResult check(TransactionRequest request) {

        Merchant merchant = merchantRepository
                .findByMerchantId(request.merchantId())
                .orElse(null);

        if (merchant == null) {

            return new FraudCheckResult(
                    true,
                    FraudRule.UNKNOWN_MERCHANT,
                    Severity.CRITICAL,
                    "Merchant does not exist."
            );
        }

        if (merchant.getStatus() != MerchantStatus.ACTIVE) {

            return new FraudCheckResult(
                    true,
                    FraudRule.BLACKLISTED_MERCHANT,
                    Severity.CRITICAL,
                    "Merchant is not active."
            );
        }
    @Override
    public FraudCheckResult check(TransactionRequest request) {

        return null;
    }
}
