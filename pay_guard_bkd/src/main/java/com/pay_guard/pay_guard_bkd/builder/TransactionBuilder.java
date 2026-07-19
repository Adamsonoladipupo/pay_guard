package com.pay_guard.pay_guard_bkd.builder;

import com.pay_guard.pay_guard_bkd.data.models.Merchant;
import com.pay_guard.pay_guard_bkd.data.models.Transaction;
import com.pay_guard.pay_guard_bkd.dtos.requests.TransactionRequest;
import com.pay_guard.pay_guard_bkd.services.model.FraudAnalysisResult;
import com.pay_guard.pay_guard_bkd.util.CardHashUtil;
import org.springframework.stereotype.Component;

@Component
public class TransactionBuilder {
    private Merchant merchant;
    private TransactionRequest request;
    private FraudAnalysisResult analysis;



    public TransactionBuilder merchant(Merchant merchant) {
        this.merchant = merchant;
        return this;
    }

    public TransactionBuilder request(TransactionRequest request) {
        this.request = request;
        return this;
    }

    public TransactionBuilder analysis(FraudAnalysisResult analysis) {
        this.analysis = analysis;
        return this;
    }

    public Transaction build() {

        Transaction transaction = new Transaction();

        transaction.setMerchant(merchant);
        transaction.setAmount(request.amount());
        transaction.setTransactionType(request.transactionType());
        transaction.setCardHash(CardHashUtil.hash(request.cardNumber()));
        transaction.setIpAddress(request.ipAddress());

        transaction.setStatus(
                analysis.recommendedStatus()
        );

        transaction.setRiskScore(
                analysis.totalRiskScore()
        );

        return transaction;
    }

    public TransactionBuilder reset() {
        merchant = null;
        request = null;
        analysis = null;
        return this;
    }
}
