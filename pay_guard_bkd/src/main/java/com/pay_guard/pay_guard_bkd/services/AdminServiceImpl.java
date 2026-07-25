package com.pay_guard.pay_guard_bkd.services;

import com.pay_guard.pay_guard_bkd.annotation.Audit;
import com.pay_guard.pay_guard_bkd.data.models.Admin;
import com.pay_guard.pay_guard_bkd.data.models.FlaggedTransaction;
import com.pay_guard.pay_guard_bkd.data.models.Transaction;
import com.pay_guard.pay_guard_bkd.data.models.emuns.InvestigationStatus;
import com.pay_guard.pay_guard_bkd.data.models.emuns.TransactionStatus;
import com.pay_guard.pay_guard_bkd.data.repository.AdminRepository;
import com.pay_guard.pay_guard_bkd.data.repository.FlaggedTransactionRepository;
import com.pay_guard.pay_guard_bkd.data.repository.MerchantRepository;
import com.pay_guard.pay_guard_bkd.data.repository.TransactionRepository;
import com.pay_guard.pay_guard_bkd.dtos.requests.ReviewRequest;
import com.pay_guard.pay_guard_bkd.dtos.responses.DashboardSummaryResponse;
import com.pay_guard.pay_guard_bkd.dtos.responses.FlaggedTransactionResponse;
import com.pay_guard.pay_guard_bkd.dtos.responses.ReviewResponse;
import com.pay_guard.pay_guard_bkd.dtos.responses.TransactionResponse;
import com.pay_guard.pay_guard_bkd.exception.AdminNotFoundException;
import com.pay_guard.pay_guard_bkd.exception.FlaggedTransactionNotFoundException;
import com.pay_guard.pay_guard_bkd.exception.TransactionNotFoundException;
import com.pay_guard.pay_guard_bkd.mappers.FlaggedTransactionMapper;
import com.pay_guard.pay_guard_bkd.mappers.TransactionMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class AdminServiceImpl implements AdminService{
    private final TransactionRepository transactionRepository;
    private final FlaggedTransactionRepository flaggedTransactionRepository;
    private final MerchantRepository merchantRepository;
    private final AdminRepository adminRepository;

    private final TransactionMapper transactionMapper;
    private final FlaggedTransactionMapper flaggedTransactionMapper;

    @Override
    @Transactional(readOnly = true)
    public DashboardSummaryResponse getDashboardSummary() {

        return new DashboardSummaryResponse(
                transactionRepository.count(),
                flaggedTransactionRepository.count(),
                transactionRepository.countByStatus(TransactionStatus.APPROVED),
                transactionRepository.countByStatus(TransactionStatus.DECLINED),
                merchantRepository.count(),
                adminRepository.count()
        );
    }

    @Override
    @Transactional(readOnly = true)
    public List<FlaggedTransactionResponse> getFlaggedTransactions() {

        return flaggedTransactionRepository.findAll()
                .stream()
                .map(flaggedTransactionMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public FlaggedTransactionResponse getFlaggedTransaction(UUID flaggedTransactionId) {
        return flaggedTransactionMapper.toResponse(
                findFlaggedTransaction(flaggedTransactionId)
        );
    }

    @Override
    @Audit("Flagged Transaction Reviewed")
    public ReviewResponse reviewTransaction(
            UUID flaggedTransactionId,
            UUID adminId,
            ReviewRequest request
    ) {

        FlaggedTransaction flaggedTransaction =
                findFlaggedTransaction(flaggedTransactionId);

        Admin admin = findAdmin(adminId);


        flaggedTransaction.review(
                admin,
                request.investigationStatus(),
                request.reviewComment()
        );


        Transaction transaction =
                flaggedTransaction.getTransaction();
        if (request.investigationStatus() == InvestigationStatus.RESOLVED) {
            transaction.setStatus(TransactionStatus.APPROVED);
        } else {
            transaction.setStatus(TransactionStatus.FLAGGED);
        }

        transactionRepository.save(transaction);

        flaggedTransactionRepository.save(flaggedTransaction);

        return new ReviewResponse(
                flaggedTransaction.getId(),
                flaggedTransaction.getInvestigationStatus(),
                flaggedTransaction.getReviewComment(),
                "Transaction reviewed successfully."

        );
    }

    @Override
    @Transactional(readOnly = true)
    public List<TransactionResponse> getTransactions() {

        return transactionRepository.findAll()
                .stream()
                .map(transactionMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public TransactionResponse getTransaction(UUID transactionId) {
        return transactionMapper.toResponse(
                findTransaction(transactionId)
        );
    }

    private FlaggedTransaction findFlaggedTransaction(UUID id) {
        return flaggedTransactionRepository.findById(id)
                .orElseThrow(() ->
                        new FlaggedTransactionNotFoundException(
                                "Flagged transaction not found."
                        )
                );
    }

    private Transaction findTransaction(UUID id) {
        return transactionRepository.findById(id)
                .orElseThrow(() ->
                        new TransactionNotFoundException(
                                "Transaction not found."
                        )
                );
    }

    private Admin findAdmin(UUID id) {
        return adminRepository.findById(id)
                .orElseThrow(() ->
                        new AdminNotFoundException(
                                "Admin not found."
                        )
                );
    }
}
