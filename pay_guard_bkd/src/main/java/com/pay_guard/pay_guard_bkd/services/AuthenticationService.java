package com.pay_guard.pay_guard_bkd.services;

import com.pay_guard.pay_guard_bkd.data.models.Admin;
import com.pay_guard.pay_guard_bkd.dtos.requests.LoginRequest;
import com.pay_guard.pay_guard_bkd.dtos.requests.RegisterAdminRequest;
import com.pay_guard.pay_guard_bkd.dtos.responses.LoginResponse;
import org.springframework.stereotype.Service;

import java.util.UUID;


public interface AuthenticationService {
    RegisterResponse registerAdmin(RegisterAdminRequest request);

    LoginResponse login(LoginRequest request);

    Admin getAdminById(UUID id);

    Admin getAdminByEmail(String email);

    boolean emailExists(String email);
}
