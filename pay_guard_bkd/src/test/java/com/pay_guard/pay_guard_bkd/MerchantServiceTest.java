package com.pay_guard.pay_guard_bkd;


import com.pay_guard.pay_guard_bkd.data.models.Merchant;
import com.pay_guard.pay_guard_bkd.data.models.emuns.MerchantCategory;
import com.pay_guard.pay_guard_bkd.data.models.emuns.MerchantStatus;
import com.pay_guard.pay_guard_bkd.data.repository.MerchantRepository;
import com.pay_guard.pay_guard_bkd.dtos.requests.MerchantRequest;
import com.pay_guard.pay_guard_bkd.dtos.responses.MerchantResponse;
import com.pay_guard.pay_guard_bkd.exception.MerchantInactiveException;
import com.pay_guard.pay_guard_bkd.exception.MerchantNotFoundException;
import com.pay_guard.pay_guard_bkd.mappers.MerchantMapper;
import com.pay_guard.pay_guard_bkd.services.MerchantServiceImp;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MerchantServiceImplTest {

    @Mock
    private MerchantRepository repository;

    @Mock
    private MerchantMapper merchantMapper;

    @InjectMocks
    private MerchantServiceImp service;

    @Test
    void shouldCreateMerchantSuccessfully() {

        MerchantRequest request = mock(MerchantRequest.class);

        Merchant merchant = new Merchant();
        merchant.setMerchantCategory(MerchantCategory.RETAIL);

        Merchant saved = new Merchant();
        saved.setMerchantId("PG-MER-12345678");

        MerchantResponse response = mock(MerchantResponse.class);

        when(merchantMapper.toEntity(request))
                .thenReturn(merchant);

        when(repository.existsByMerchantId(any()))
                .thenReturn(false);

        when(repository.save(any(Merchant.class)))
                .thenReturn(saved);

        when(merchantMapper.toResponse(saved))
                .thenReturn(response);

        MerchantResponse result =
                service.createMerchant(request);

        assertThat(result).isEqualTo(response);

        verify(repository).save(any(Merchant.class));
    }

    @Test
    void shouldAssignOtherCategoryWhenCategoryIsNull() {

        MerchantRequest request = mock(MerchantRequest.class);

        Merchant merchant = new Merchant();
        merchant.setMerchantCategory(null);

        Merchant saved = new Merchant();

        when(merchantMapper.toEntity(request))
                .thenReturn(merchant);

        when(repository.existsByMerchantId(any()))
                .thenReturn(false);

        when(repository.save(any()))
                .thenReturn(saved);

        when(merchantMapper.toResponse(saved))
                .thenReturn(mock(MerchantResponse.class));

        service.createMerchant(request);

        ArgumentCaptor<Merchant> captor =
                ArgumentCaptor.forClass(Merchant.class);

        verify(repository).save(captor.capture());

        assertThat(captor.getValue().getMerchantCategory())
                .isEqualTo(MerchantCategory.OTHER);
    }

    @Test
    void shouldGenerateMerchantIdBeforeSaving() {

        MerchantRequest request = mock(MerchantRequest.class);

        Merchant merchant = new Merchant();

        when(merchantMapper.toEntity(request))
                .thenReturn(merchant);

        when(repository.existsByMerchantId(any()))
                .thenReturn(false);

        when(repository.save(any()))
                .thenAnswer(invocation -> invocation.getArgument(0));

        when(merchantMapper.toResponse(any()))
                .thenReturn(mock(MerchantResponse.class));

        service.createMerchant(request);

        ArgumentCaptor<Merchant> captor =
                ArgumentCaptor.forClass(Merchant.class);

        verify(repository).save(captor.capture());

        assertThat(captor.getValue().getMerchantId())
                .startsWith("PG-MER-");
    }

    @Test
    void shouldReturnMerchantById() {

        UUID id = UUID.randomUUID();

        Merchant merchant = new Merchant();

        MerchantResponse response =
                mock(MerchantResponse.class);

        when(repository.findById(id))
                .thenReturn(Optional.of(merchant));

        when(merchantMapper.toResponse(merchant))
                .thenReturn(response);

        MerchantResponse result =
                service.getMerchant(id);

        assertThat(result).isEqualTo(response);
    }

    @Test
    void shouldThrowWhenMerchantDoesNotExist() {

        UUID id = UUID.randomUUID();

        when(repository.findById(id))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() ->
                service.getMerchant(id))
                .isInstanceOf(MerchantNotFoundException.class);
    }

    @Test
    void shouldReturnAllMerchants() {

        Merchant merchant = new Merchant();

        MerchantResponse response =
                mock(MerchantResponse.class);

        when(repository.findAll())
                .thenReturn(List.of(merchant));

        when(merchantMapper.toResponse(merchant))
                .thenReturn(response);

        List<MerchantResponse> results =
                service.getMerchants();

        assertThat(results).hasSize(1);

        verify(repository).findAll();
    }

    @Test
    void shouldReturnActiveMerchant() {

        Merchant merchant = new Merchant();
        merchant.setStatus(MerchantStatus.ACTIVE);

        when(repository.findByMerchantId("PG-MER-111"))
                .thenReturn(Optional.of(merchant));

        Merchant result =
                service.validateAndGetMerchant("PG-MER-111");

        assertThat(result).isEqualTo(merchant);
    }

    @Test
    void shouldThrowWhenMerchantIsInactive() {

        Merchant merchant = new Merchant();
        merchant.setStatus(MerchantStatus.SUSPENDED);

        when(repository.findByMerchantId("PG-MER-111"))
                .thenReturn(Optional.of(merchant));

        assertThatThrownBy(() ->
                service.validateAndGetMerchant("PG-MER-111"))
                .isInstanceOf(MerchantInactiveException.class);
    }

    @Test
    void shouldThrowWhenMerchantIdDoesNotExist() {

        when(repository.findByMerchantId("PG-MER-111"))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() ->
                service.validateAndGetMerchant("PG-MER-111"))
                .isInstanceOf(MerchantNotFoundException.class);
    }

    @Test
    void shouldGenerateAnotherMerchantIdWhenDuplicateExists() {

        MerchantRequest request = mock(MerchantRequest.class);

        Merchant merchant = new Merchant();

        when(merchantMapper.toEntity(request))
                .thenReturn(merchant);

        when(repository.existsByMerchantId(any()))
                .thenReturn(true)
                .thenReturn(false);

        when(repository.save(any()))
                .thenAnswer(invocation -> invocation.getArgument(0));

        when(merchantMapper.toResponse(any()))
                .thenReturn(mock(MerchantResponse.class));

        service.createMerchant(request);

        verify(repository, times(2))
                .existsByMerchantId(any());
    }
}