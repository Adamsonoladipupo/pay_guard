package com.pay_guard.pay_guard_bkd;

import com.pay_guard.pay_guard_bkd.data.models.Admin;
import com.pay_guard.pay_guard_bkd.data.models.emuns.UserRole;
import com.pay_guard.pay_guard_bkd.data.repository.AdminRepository;
import com.pay_guard.pay_guard_bkd.dtos.requests.RegisterAdminRequest;
import com.pay_guard.pay_guard_bkd.dtos.responses.RegisterResponse;
import com.pay_guard.pay_guard_bkd.exception.BusinessException;
import com.pay_guard.pay_guard_bkd.mappers.AdminMapper;
import com.pay_guard.pay_guard_bkd.services.AuthenticationServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.UUID;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AuthenticationServiceImplTest {
    @Mock
    private AdminRepository adminRepository;

    @Mock
    private PasswordEncoder passwordEncoder;


    @Mock
    private AdminMapper adminMapper;


    @InjectMocks
    private AuthenticationServiceImpl service;

    @Test
    void shouldRegisterAdminSuccessfully() {

        RegisterAdminRequest request =
                new RegisterAdminRequest(
                        "Adamson",
                        "Oladipupo",
                        "admin@payguard.com",
                        "password"
                );

        Admin admin = new Admin();

        admin.setId(UUID.randomUUID());
        admin.setEmail(request.email());
        admin.setPassword("encoded");
        admin.setRole(UserRole.ADMIN);

        RegisterResponse response =
                new RegisterResponse(
                        admin.getId(),
                        admin.getFirstName(),
                        admin.getLastName(),
                        admin.getEmail(),
                        admin.getRole(),
                        "Admin registered successfully."
                );

        when(adminRepository.existsByEmail(request.email()))
                .thenReturn(false);

        when(adminMapper.toEntity(request))
                .thenReturn(admin);

        when(passwordEncoder.encode(request.password()))
                .thenReturn("encoded");

        when(adminRepository.save(any(Admin.class)))
                .thenReturn(admin);

        when(adminMapper.toRegisterResponse(admin))
                .thenReturn(response);

        RegisterResponse result = service.registerAdmin(request);

        assertThat(result.email()).isEqualTo(request.email());

        verify(adminRepository).save(any(Admin.class));
    }

    @Test
    void shouldThrowExceptionWhenEmailAlreadyExists() {

        RegisterAdminRequest request =
                new RegisterAdminRequest(
                        "John",
                        "Doe",
                        "admin@payguard.com",
                        "password"
                );

        when(adminRepository.existsByEmail(request.email()))
                .thenReturn(true);

        assertThatThrownBy(() ->
                service.registerAdmin(request))
                .isInstanceOf(BusinessException.class);

        verify(adminRepository, never()).save(any());
    }


}
