package com.pay_guard.pay_guard_bkd.dtos.responses;

import com.pay_guard.pay_guard_bkd.data.models.MerchantCategory;
import com.pay_guard.pay_guard_bkd.data.models.MerchantStatus;

import java.util.UUID;

public record MerchantResponse(
        UUID id,
        String merchantId,
        String merchantName,
        MerchantCategory merchantCategory,
        MerchantStatus status
) {}
