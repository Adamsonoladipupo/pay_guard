package com.pay_guard.pay_guard_bkd.services;

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

                transactionRepository.countApprovedTransactions(),

                transactionRepository.countRejectedTransactions(),

                merchantRepository.count(),

                adminRepository.count()

        );
    }
    
}
