package com.pay_guard.pay_guard_bkd;

import com.pay_guard.pay_guard_bkd.data.models.Admin;
import com.pay_guard.pay_guard_bkd.data.models.emuns.UserRole;
import com.pay_guard.pay_guard_bkd.data.repository.AdminRepository;
import com.pay_guard.pay_guard_bkd.dtos.requests.LoginRequest;
import com.pay_guard.pay_guard_bkd.dtos.requests.RegisterAdminRequest;
import com.pay_guard.pay_guard_bkd.dtos.responses.LoginResponse;
import com.pay_guard.pay_guard_bkd.dtos.responses.RegisterResponse;
import com.pay_guard.pay_guard_bkd.exception.BusinessException;
import com.pay_guard.pay_guard_bkd.mappers.AdminMapper;
import com.pay_guard.pay_guard_bkd.security.AdminDetails;
import com.pay_guard.pay_guard_bkd.security.JwtService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class AuthenticationServiceImplTest {
}
