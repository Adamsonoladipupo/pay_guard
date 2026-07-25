package com.pay_guard.pay_guard_bkd;

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
import com.pay_guard.pay_guard_bkd.services.AdminServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AdminServiceImplTest {

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private FlaggedTransactionRepository flaggedTransactionRepository;

    @Mock
    private MerchantRepository merchantRepository;

    @Mock
    private AdminRepository adminRepository;

    @Mock
    private TransactionMapper transactionMapper;

    @Mock
    private FlaggedTransactionMapper flaggedTransactionMapper;

    @InjectMocks
    private AdminServiceImpl service;

    @Test
    void shouldReturnDashboardSummary() {

        when(transactionRepository.count()).thenReturn(15L);
        when(flaggedTransactionRepository.count()).thenReturn(4L);
        when(transactionRepository.countByStatus(TransactionStatus.APPROVED)).thenReturn(10L);
        when(transactionRepository.countByStatus(TransactionStatus.DECLINED)).thenReturn(1L);
        when(merchantRepository.count()).thenReturn(6L);
        when(adminRepository.count()).thenReturn(2L);

        DashboardSummaryResponse response = service.getDashboardSummary();

        assertThat(response.totalTransactions()).isEqualTo(15);
        assertThat(response.totalFlaggedTransactions()).isEqualTo(4);
        assertThat(response.totalApprovedTransactions()).isEqualTo(10);
        assertThat(response.totalRejectedTransactions()).isEqualTo(1);
        assertThat(response.totalMerchants()).isEqualTo(6);
        assertThat(response.totalAdmins()).isEqualTo(2);
    }

    @Test
    void shouldReturnAllFlaggedTransactions() {

        FlaggedTransaction flagged = new FlaggedTransaction();

        FlaggedTransactionResponse response =
                mock(FlaggedTransactionResponse.class);

        when(flaggedTransactionRepository.findAll())
                .thenReturn(List.of(flagged));

        when(flaggedTransactionMapper.toResponse(flagged))
                .thenReturn(response);

        List<FlaggedTransactionResponse> results =
                service.getFlaggedTransactions();

        assertThat(results).hasSize(1);

        verify(flaggedTransactionRepository).findAll();
    }

    @Test
    void shouldReturnFlaggedTransactionById() {

        UUID id = UUID.randomUUID();

        FlaggedTransaction flagged = new FlaggedTransaction();

        FlaggedTransactionResponse response =
                mock(FlaggedTransactionResponse.class);

        when(flaggedTransactionRepository.findById(id))
                .thenReturn(Optional.of(flagged));

        when(flaggedTransactionMapper.toResponse(flagged))
                .thenReturn(response);

        assertThat(service.getFlaggedTransaction(id))
                .isEqualTo(response);
    }

    @Test
    void shouldThrowWhenFlaggedTransactionDoesNotExist() {

        UUID id = UUID.randomUUID();

        when(flaggedTransactionRepository.findById(id))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() ->
                service.getFlaggedTransaction(id))
                .isInstanceOf(FlaggedTransactionNotFoundException.class)
                .hasMessage("Flagged transaction not found.");
    }

    @Test
    void shouldReturnAllTransactions() {

        Transaction transaction = new Transaction();

        TransactionResponse response =
                mock(TransactionResponse.class);

        when(transactionRepository.findAll())
                .thenReturn(List.of(transaction));

        when(transactionMapper.toResponse(transaction))
                .thenReturn(response);

        List<TransactionResponse> results =
                service.getTransactions();

        assertThat(results).hasSize(1);

        verify(transactionRepository).findAll();
    }

    @Test
    void shouldReturnTransactionById() {

        UUID id = UUID.randomUUID();

        Transaction transaction = new Transaction();

        TransactionResponse response =
                mock(TransactionResponse.class);

        when(transactionRepository.findById(id))
                .thenReturn(Optional.of(transaction));

        when(transactionMapper.toResponse(transaction))
                .thenReturn(response);

        assertThat(service.getTransaction(id))
                .isEqualTo(response);
    }

    @Test
    void shouldThrowWhenTransactionNotFound() {

        UUID id = UUID.randomUUID();

        when(transactionRepository.findById(id))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() ->
                service.getTransaction(id))
                .isInstanceOf(TransactionNotFoundException.class)
                .hasMessage("Transaction not found.");
    }

    @Test
    void shouldReviewResolvedTransactionSuccessfully() {

        UUID flaggedId = UUID.randomUUID();
        UUID adminId = UUID.randomUUID();

        Admin admin = new Admin();

        Transaction transaction = new Transaction();

        FlaggedTransaction flagged = new FlaggedTransaction();
        flagged.setTransaction(transaction);

        when(flaggedTransactionRepository.findById(flaggedId))
                .thenReturn(Optional.of(flagged));

        when(adminRepository.findById(adminId))
                .thenReturn(Optional.of(admin));

        ReviewRequest request =
                new ReviewRequest(
                        InvestigationStatus.RESOLVED,
                        "Looks legitimate."
                );

        ReviewResponse response =
                service.reviewTransaction(
                        flaggedId,
                        adminId,
                        request
                );

        assertThat(response).isNotNull();

        assertThat(transaction.getStatus())
                .isEqualTo(TransactionStatus.APPROVED);

        verify(transactionRepository).save(transaction);

        verify(flaggedTransactionRepository)
                .save(flagged);
    }

    @Test
    void shouldKeepTransactionFlaggedWhenNotResolved() {

        UUID flaggedId = UUID.randomUUID();
        UUID adminId = UUID.randomUUID();

        Admin admin = new Admin();

        Transaction transaction = new Transaction();

        FlaggedTransaction flagged = new FlaggedTransaction();
        flagged.setTransaction(transaction);

        when(flaggedTransactionRepository.findById(flaggedId))
                .thenReturn(Optional.of(flagged));

        when(adminRepository.findById(adminId))
                .thenReturn(Optional.of(admin));

        ReviewRequest request =
                new ReviewRequest(
                        InvestigationStatus.IN_PROGRESS,
                        "Investigating..."
                );

        service.reviewTransaction(
                flaggedId,
                adminId,
                request
        );

        assertThat(transaction.getStatus())
                .isEqualTo(TransactionStatus.FLAGGED);

        verify(transactionRepository).save(transaction);

        verify(flaggedTransactionRepository)
                .save(flagged);
    }

    @Test
    void shouldThrowWhenAdminNotFound() {

        UUID flaggedId = UUID.randomUUID();
        UUID adminId = UUID.randomUUID();

        FlaggedTransaction flagged =
                new FlaggedTransaction();

        when(flaggedTransactionRepository.findById(flaggedId))
                .thenReturn(Optional.of(flagged));

        when(adminRepository.findById(adminId))
                .thenReturn(Optional.empty());

        ReviewRequest request =
                new ReviewRequest(
                        InvestigationStatus.RESOLVED,
                        "Done"
                );

        assertThatThrownBy(() ->
                service.reviewTransaction(
                        flaggedId,
                        adminId,
                        request
                ))
                .isInstanceOf(AdminNotFoundException.class);
    }
}