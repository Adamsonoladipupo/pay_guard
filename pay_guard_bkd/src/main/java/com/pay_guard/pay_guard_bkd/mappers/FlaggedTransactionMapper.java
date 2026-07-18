package com.pay_guard.pay_guard_bkd.mappers;

public interface FlaggedTransactionMapper {
    @Mapping(target = "transactionId", source = "transaction.id")
    FlaggedTransactionResponse toResponse(
            FlaggedTransaction flaggedTransaction
    );
}
