package com.pay_guard.pay_guard_bkd.services;

import com.pay_guard.pay_guard_bkd.builder.FlaggedTransactionBuilder;
import com.pay_guard.pay_guard_bkd.builder.TransactionBuilder;
import com.pay_guard.pay_guard_bkd.data.models.FlaggedTransaction;
import com.pay_guard.pay_guard_bkd.data.models.Merchant;
import com.pay_guard.pay_guard_bkd.data.models.Transaction;
import com.pay_guard.pay_guard_bkd.data.repository.FlaggedTransactionRepository;
import com.pay_guard.pay_guard_bkd.data.repository.TransactionRepository;
import com.pay_guard.pay_guard_bkd.dtos.requests.TransactionRequest;
import com.pay_guard.pay_guard_bkd.dtos.responses.TransactionResponse;
import com.pay_guard.pay_guard_bkd.event.TransactionProcessedEvent;
import com.pay_guard.pay_guard_bkd.mappers.TransactionMapper;
import com.pay_guard.pay_guard_bkd.services.model.FraudAnalysisResult;
import com.pay_guard.pay_guard_bkd.strategy.FraudCheckResult;
import jakarta.transaction.Transactional;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class TransactionServiceImp {
    private final TransactionRepository transactionRepository;
    private final MerchantServiceImp merchantServiceImp;
    private final FraudDetectionService fraudDetectionService;
    private final FlaggedTransactionRepository flaggedRepository;
    private final TransactionMapper transactionMapper;
    private final ApplicationEventPublisher eventPublisher;

    public TransactionServiceImp(
            TransactionRepository transactionRepository,
            MerchantServiceImp merchantServiceImp,
            FraudDetectionService fraudDetectionService,
            FlaggedTransactionRepository flaggedRepository,
            TransactionMapper transactionMapper,
            ApplicationEventPublisher eventPublisher
    ) {
        this.transactionRepository = transactionRepository;
        this.merchantServiceImp = merchantServiceImp;
        this.fraudDetectionService = fraudDetectionService;
        this.flaggedRepository = flaggedRepository;
        this.transactionMapper = transactionMapper;
        this.eventPublisher = eventPublisher;
    }

    public TransactionResponse createTransaction(TransactionRequest request) {

        Merchant merchant = validateMerchant(request);

        FraudAnalysisResult analysis = analyzeTransaction(request);

        Transaction transaction =
                buildTransaction(
                        merchant,
                        request,
                        analysis
                );

        transaction = saveTransaction(transaction);

        saveFlaggedTransactions(transaction, analysis);

        publishTransactionEvent(transaction);

        return mapResponse(transaction);
    }

    private Merchant validateMerchant(TransactionRequest request) {
        return merchantServiceImp.validateAndGetMerchant(request.merchantId());
    }

    private FraudAnalysisResult analyzeTransaction(TransactionRequest request) {
        return fraudDetectionService.analyze(request);
    }

    private Transaction buildTransaction(
            Merchant merchant,
            TransactionRequest request,
            FraudAnalysisResult analysis
    ) {

        return new TransactionBuilder()
                .merchant(merchant)
                .request(request)
                .analysis(analysis)
                .build();
    }

    private Transaction saveTransaction(
            Transaction transaction
    ) {
        return transactionRepository.save(transaction);
    }

    private void saveFlaggedTransactions(
            Transaction transaction,
            FraudAnalysisResult analysis
    ) {

        if (!analysis.fraudDetected()) {
            return;
        }

        for (FraudCheckResult result :
                analysis.triggeredRules()) {

            FlaggedTransaction flaggedTransaction =
                    new FlaggedTransactionBuilder()
                            .transaction(transaction)
                            .result(result)
                            .build();

            flaggedRepository.save(flaggedTransaction);
        }
    }

    private void publishTransactionEvent(Transaction transaction) {
        eventPublisher.publishEvent(
                new TransactionProcessedEvent(transaction)
        );
    }

    private TransactionResponse mapResponse(
            Transaction transaction
    ) {
        return transactionMapper.toResponse(transaction);
    }
}
