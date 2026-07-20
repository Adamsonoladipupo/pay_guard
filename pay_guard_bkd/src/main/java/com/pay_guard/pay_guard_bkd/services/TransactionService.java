package com.pay_guard.pay_guard_bkd.services;

import com.pay_guard.pay_guard_bkd.data.models.Merchant;
import com.pay_guard.pay_guard_bkd.dtos.requests.MerchantRequest;
import com.pay_guard.pay_guard_bkd.dtos.requests.TransactionRequest;
import com.pay_guard.pay_guard_bkd.dtos.responses.MerchantResponse;
import com.pay_guard.pay_guard_bkd.dtos.responses.TransactionResponse;

import java.util.List;
import java.util.UUID;

public interface TransactionService {
    TransactionResponse createTransaction(TransactionRequest request);
    TransactionResponse getTransaction(UUID transactionId);
    List<TransactionResponse> getTransactions();
}
