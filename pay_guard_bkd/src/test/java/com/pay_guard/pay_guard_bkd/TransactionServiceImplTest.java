package com.pay_guard.pay_guard_bkd;

import com.pay_guard.pay_guard_bkd.data.models.Merchant;
import com.pay_guard.pay_guard_bkd.data.models.Transaction;
import com.pay_guard.pay_guard_bkd.data.repository.FlaggedTransactionRepository;
import com.pay_guard.pay_guard_bkd.data.repository.TransactionRepository;
import com.pay_guard.pay_guard_bkd.dtos.requests.TransactionRequest;
import com.pay_guard.pay_guard_bkd.dtos.responses.TransactionResponse;
import com.pay_guard.pay_guard_bkd.event.TransactionProcessedEvent;
import com.pay_guard.pay_guard_bkd.exception.TransactionNotFoundException;
import com.pay_guard.pay_guard_bkd.mappers.TransactionMapper;
import com.pay_guard.pay_guard_bkd.services.FraudDetectionService;
import com.pay_guard.pay_guard_bkd.services.MerchantService;
import com.pay_guard.pay_guard_bkd.services.TransactionServiceImp;
import com.pay_guard.pay_guard_bkd.services.model.FraudAnalysisResult;
import com.pay_guard.pay_guard_bkd.strategy.FraudCheckResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TransactionServiceImplTest {

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private MerchantService merchantService;

    @Mock
    private FraudDetectionService fraudDetectionService;

    @Mock
    private FlaggedTransactionRepository flaggedRepository;

    @Mock
    private TransactionMapper transactionMapper;

    @Mock
    private ApplicationEventPublisher eventPublisher;

    @InjectMocks
    private TransactionServiceImp service;

    private Merchant merchant;
    private Transaction transaction;
    private TransactionRequest request;

    @BeforeEach
    void setup() {

        merchant = new Merchant();
        merchant.setMerchantId("PG-MER-12345678");

        transaction = new Transaction();
        transaction.setId(UUID.randomUUID());

        request = new TransactionRequest(
                "4084084084084081",
                BigDecimal.valueOf(25000),
                "PG-MER-12345678",
                "192.168.1.10",
                "NGN",
                null,
                "DEVICE001"
        );
    }

    @Test
    void shouldCreateApprovedTransactionSuccessfully() {

        FraudAnalysisResult analysis =
                new FraudAnalysisResult(
                        false,
                        0,
                        null,
                        Collections.emptyList()
                );

        TransactionResponse response =
                new TransactionResponse(
                        transaction.getId(),
                        "4084********4081",
                        BigDecimal.valueOf(25000),
                        "NGN",
                        merchant.getMerchantId(),
                        0,
                        null,
                        null
                );

        when(merchantService.validateAndGetMerchant(request.merchantId()))
                .thenReturn(merchant);

        when(fraudDetectionService.analyze(request))
                .thenReturn(analysis);

        when(transactionRepository.save(any(Transaction.class)))
                .thenReturn(transaction);

        when(transactionMapper.toResponse(transaction))
                .thenReturn(response);

        TransactionResponse result =
                service.createTransaction(request);

        assertThat(result).isNotNull();

        verify(transactionRepository).save(any(Transaction.class));

        verify(flaggedRepository, never()).save(any());

        verify(eventPublisher)
                .publishEvent(any(TransactionProcessedEvent.class));
    }

    @Test
    void shouldPublishTransactionEvent() {

        FraudAnalysisResult analysis =
                new FraudAnalysisResult(
                        false,
                        0,
                        null,
                        Collections.emptyList()
                );

        when(merchantService.validateAndGetMerchant(any()))
                .thenReturn(merchant);

        when(fraudDetectionService.analyze(any()))
                .thenReturn(analysis);

        when(transactionRepository.save(any()))
                .thenReturn(transaction);

        when(transactionMapper.toResponse(any()))
                .thenReturn(mock(TransactionResponse.class));

        service.createTransaction(request);

        ArgumentCaptor<TransactionProcessedEvent> captor =
                ArgumentCaptor.forClass(TransactionProcessedEvent.class);

        verify(eventPublisher)
                .publishEvent(captor.capture());

        assertThat(captor.getValue()).isNotNull();
    }

    @Test
    void shouldCallFraudDetectionService() {

        FraudAnalysisResult analysis =
                new FraudAnalysisResult(
                        false,
                        0,
                        null,
                        Collections.emptyList()
                );

        when(merchantService.validateAndGetMerchant(any()))
                .thenReturn(merchant);

        when(fraudDetectionService.analyze(any()))
                .thenReturn(analysis);

        when(transactionRepository.save(any()))
                .thenReturn(transaction);

        when(transactionMapper.toResponse(any()))
                .thenReturn(mock(TransactionResponse.class));

        service.createTransaction(request);

        verify(fraudDetectionService)
                .analyze(request);
    }

    @Test
    void shouldValidateMerchantBeforeCreatingTransaction() {

        FraudAnalysisResult analysis =
                new FraudAnalysisResult(
                        false,
                        0,
                        null,
                        Collections.emptyList()
                );

        when(merchantService.validateAndGetMerchant(any()))
                .thenReturn(merchant);

        when(fraudDetectionService.analyze(any()))
                .thenReturn(analysis);

        when(transactionRepository.save(any()))
                .thenReturn(transaction);

        when(transactionMapper.toResponse(any()))
                .thenReturn(mock(TransactionResponse.class));

        service.createTransaction(request);

        verify(merchantService)
                .validateAndGetMerchant(request.merchantId());
    }

    @Test
    void shouldReturnTransactionById() {

        TransactionResponse response =
                mock(TransactionResponse.class);

        when(transactionRepository.findById(transaction.getId()))
                .thenReturn(Optional.of(transaction));

        when(transactionMapper.toResponse(transaction))
                .thenReturn(response);

        TransactionResponse result =
                service.getTransaction(transaction.getId());

        assertThat(result).isEqualTo(response);
    }

    @Test
    void shouldThrowWhenTransactionDoesNotExist() {

        UUID id = UUID.randomUUID();

        when(transactionRepository.findById(id))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() ->
                service.getTransaction(id))
                .isInstanceOf(TransactionNotFoundException.class);
    }

    @Test
    void shouldReturnAllTransactions() {

        when(transactionRepository.findAll())
                .thenReturn(List.of(transaction));

        when(transactionMapper.toResponse(transaction))
                .thenReturn(mock(TransactionResponse.class));

        List<TransactionResponse> responses =
                service.getTransactions();

        assertThat(responses).hasSize(1);

        verify(transactionRepository).findAll();
    }

}
