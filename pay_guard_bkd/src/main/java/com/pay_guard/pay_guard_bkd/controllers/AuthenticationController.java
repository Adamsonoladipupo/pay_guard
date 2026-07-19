package com.pay_guard.pay_guard_bkd.controllers;

import com.pay_guard.pay_guard_bkd.dtos.requests.LoginRequest;
import com.pay_guard.pay_guard_bkd.dtos.requests.RegisterAdminRequest;
import com.pay_guard.pay_guard_bkd.dtos.responses.LoginResponse;
import com.pay_guard.pay_guard_bkd.dtos.responses.RegisterResponse;
import com.pay_guard.pay_guard_bkd.services.AuthenticationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> register(
            @Valid @RequestBody RegisterAdminRequest request) {

        RegisterResponse response =
                authenticationService.registerAdmin(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(
            @Valid @RequestBody LoginRequest request) {

        LoginResponse response =
                authenticationService.login(request);

        return ResponseEntity.ok(response);
    }
}
