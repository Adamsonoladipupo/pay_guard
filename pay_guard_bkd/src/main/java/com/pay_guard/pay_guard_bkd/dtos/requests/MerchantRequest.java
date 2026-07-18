package com.pay_guard.pay_guard_bkd.dtos.requests;

import com.pay_guard.pay_guard_bkd.data.models.emuns.MerchantCategory;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record MerchantRequest(
        @NotBlank
        String merchantId,

        @NotBlank
        String merchantName,

        @NotNull
        MerchantCategory merchantCategory
) {}
