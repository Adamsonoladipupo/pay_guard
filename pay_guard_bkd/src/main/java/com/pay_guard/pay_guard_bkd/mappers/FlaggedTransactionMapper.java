package com.pay_guard.pay_guard_bkd.mappers;

import com.pay_guard.pay_guard_bkd.data.models.FlaggedTransaction;
import com.pay_guard.pay_guard_bkd.dtos.responses.FlaggedTransactionResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface FlaggedTransactionMapper {
    @Mapping(target = "transactionId", source = "transaction.id")
    FlaggedTransactionResponse toResponse(
            FlaggedTransaction flaggedTransaction
    );
}
