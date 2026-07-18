package com.pay_guard.pay_guard_bkd.mappers;

import com.pay_guard.pay_guard_bkd.data.models.Merchant;
import com.pay_guard.pay_guard_bkd.dtos.requests.MerchantRequest;
import com.pay_guard.pay_guard_bkd.dtos.responses.MerchantResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MerchantMapper {
    Merchant toEntity(MerchantRequest request);
    MerchantResponse toResponse(Merchant merchant);
}
