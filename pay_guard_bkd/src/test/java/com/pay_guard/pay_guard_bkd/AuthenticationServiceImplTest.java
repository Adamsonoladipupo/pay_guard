package com.pay_guard.pay_guard_bkd;

import com.pay_guard.pay_guard_bkd.data.models.Admin;
import com.pay_guard.pay_guard_bkd.data.models.emuns.UserRole;
import com.pay_guard.pay_guard_bkd.data.repository.AdminRepository;
import com.pay_guard.pay_guard_bkd.dtos.requests.LoginRequest;
import com.pay_guard.pay_guard_bkd.dtos.requests.RegisterAdminRequest;
import com.pay_guard.pay_guard_bkd.dtos.responses.LoginResponse;
import com.pay_guard.pay_guard_bkd.dtos.responses.RegisterResponse;
import com.pay_guard.pay_guard_bkd.exception.BusinessException;
import com.pay_guard.pay_guard_bkd.exception.InvalidCredentialsException;
import com.pay_guard.pay_guard_bkd.mappers.AdminMapper;
import com.pay_guard.pay_guard_bkd.security.AdminDetails;
import com.pay_guard.pay_guard_bkd.security.JwtService;
import com.pay_guard.pay_guard_bkd.services.AuthenticationServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;
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

    @Mock
    private JwtService jwtService;

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

    @Test
    void shouldLoginSuccessfully() {

        LoginRequest request = new LoginRequest(
                "admin@payguard.com",
                "password"
        );

        Admin admin = new Admin();

        admin.setEmail(request.email());

        UserDetails userDetails =
                mock(UserDetails.class);

        when(adminRepository.findByEmail(request.email()))
                .thenReturn(Optional.of(admin));

        when(adminDetailsService.loadUserByUsername(request.email()))
                .thenReturn(userDetails);

        when(jwtService.generateToken(userDetails))
                .thenReturn("jwt-token");

        LoginResponse response = service.login(request);

        assertThat(response).isNotNull();

        assertThat(response.token())
                .isEqualTo("jwt-token");

        assertThat(response.type())
                .isEqualTo("Bearer");

        verify(authenticationManager)
                .authenticate(any(UsernamePasswordAuthenticationToken.class));

        verify(jwtService)
                .generateToken(userDetails);
    }

    @Test
    void shouldThrowExceptionWhenAdminDoesNotExist() {

        LoginRequest request = new LoginRequest(
                "missing@payguard.com",
                "password"
        );

        when(adminRepository.findByEmail(request.email()))
                .thenReturn(Optional.empty());

        doNothing().when(authenticationManager)
                .authenticate(any());

        assertThatThrownBy(() ->
                service.login(request))
                .isInstanceOf(InvalidCredentialsException.class);
    }

    @Test
    void shouldThrowExceptionWhenPasswordIsIncorrect() {

        LoginRequest request =
                new LoginRequest(
                        "admin@payguard.com",
                        "wrong-password"
                );

        Admin admin = new Admin();

        admin.setEmail(request.email());

        admin.setPassword("encoded");

        when(adminRepository.findByEmail(request.email()))
                .thenReturn(Optional.of(admin));

        when(passwordEncoder.matches(
                request.password(),
                admin.getPassword()
        )).thenReturn(false);

        assertThatThrownBy(() ->
                service.login(request))
                .isInstanceOf(BusinessException.class);
    }
}
