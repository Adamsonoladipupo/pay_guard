package com.pay_guard.pay_guard_bkd.mappers;

import com.pay_guard.pay_guard_bkd.data.models.Transaction;
import com.pay_guard.pay_guard_bkd.dtos.requests.TransactionRequest;
import com.pay_guard.pay_guard_bkd.dtos.responses.TransactionResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TransactionMapper {
    Transaction toEntity(TransactionRequest request);
    @Mapping(target = "maskedCardNumber", ignore = true)
    @Mapping(target = "merchantId", source = "merchant.merchantId")
    TransactionResponse toResponse(Transaction transaction);
}
