package com.pay_guard.pay_guard_bkd.services;

import com.pay_guard.pay_guard_bkd.dtos.requests.ReviewRequest;
import com.pay_guard.pay_guard_bkd.dtos.responses.DashboardSummaryResponse;
import com.pay_guard.pay_guard_bkd.dtos.responses.FlaggedTransactionResponse;
import com.pay_guard.pay_guard_bkd.dtos.responses.ReviewResponse;
import com.pay_guard.pay_guard_bkd.dtos.responses.TransactionResponse;

import java.util.List;
import java.util.UUID;

public interface AdminService {
    DashboardSummaryResponse getDashboardSummary();

    List<FlaggedTransactionResponse> getFlaggedTransactions();

    FlaggedTransactionResponse getFlaggedTransaction(UUID flaggedTransactionId);

    ReviewResponse reviewTransaction(
            UUID flaggedTransactionId,
            UUID adminId,
            ReviewRequest request
    );

    List<TransactionResponse> getTransactions();

    TransactionResponse getTransaction(UUID transactionId);
}
