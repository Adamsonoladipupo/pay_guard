package com.pay_guard.pay_guard_bkd.services;

import com.pay_guard.pay_guard_bkd.data.repository.FlaggedTransactionRepository;
import com.pay_guard.pay_guard_bkd.data.repository.TransactionRepository;
import com.pay_guard.pay_guard_bkd.mappers.TransactionMapper;
import jakarta.transaction.Transactional;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class TransactionService {
    private final TransactionRepository transactionRepository;
    private final MerchantService merchantService;
    private final FraudDetectionService fraudDetectionService;
    private final FlaggedTransactionRepository flaggedRepository;
    private final TransactionMapper transactionMapper;
    private final ApplicationEventPublisher eventPublisher;

    public TransactionService(
            TransactionRepository transactionRepository,
            MerchantService merchantService,
            FraudDetectionService fraudDetectionService,
            FlaggedTransactionRepository flaggedRepository,
            TransactionMapper transactionMapper,
            ApplicationEventPublisher eventPublisher
    ) {
        this.transactionRepository = transactionRepository;
        this.merchantService = merchantService;
        this.fraudDetectionService = fraudDetectionService;
        this.flaggedRepository = flaggedRepository;
        this.transactionMapper = transactionMapper;
        this.eventPublisher = eventPublisher;
    }

    
}
