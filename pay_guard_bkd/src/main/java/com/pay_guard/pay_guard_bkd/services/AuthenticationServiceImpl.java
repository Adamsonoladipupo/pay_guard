package com.pay_guard.pay_guard_bkd.services;

import com.pay_guard.pay_guard_bkd.annotation.Audit;
import com.pay_guard.pay_guard_bkd.data.models.Admin;
import com.pay_guard.pay_guard_bkd.data.models.emuns.UserRole;
import com.pay_guard.pay_guard_bkd.data.repository.AdminRepository;
import com.pay_guard.pay_guard_bkd.dtos.requests.LoginRequest;
import com.pay_guard.pay_guard_bkd.dtos.requests.RegisterAdminRequest;
import com.pay_guard.pay_guard_bkd.dtos.responses.LoginResponse;
import com.pay_guard.pay_guard_bkd.dtos.responses.RegisterResponse;
import com.pay_guard.pay_guard_bkd.exception.AdminNotFoundException;
import com.pay_guard.pay_guard_bkd.exception.EmailAlreadyExistsException;
import com.pay_guard.pay_guard_bkd.exception.InvalidCredentialsException;
import com.pay_guard.pay_guard_bkd.mappers.AdminMapper;
import com.pay_guard.pay_guard_bkd.security.AdminDetailsService;
import com.pay_guard.pay_guard_bkd.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthenticationServiceImpl implements AuthenticationService{
    private final AdminRepository adminRepository;
    private final AdminMapper adminMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final AdminDetailsService adminDetailsService;

    @Override
    @Audit("Admin Registered")
    public RegisterResponse registerAdmin(RegisterAdminRequest request) {
        validateEmail(request.email());
        Admin admin = createAdmin(request);
        Admin savedAdmin = adminRepository.save(admin);
        return adminMapper.toRegisterResponse(savedAdmin);
    }

    @Override
    @Audit("Admin Login")
    @Transactional(readOnly = true)
    public LoginResponse login(LoginRequest request) {

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.email(), request.password()));

        Admin admin = findAdminByEmail(request.email());

        UserDetails userDetails = adminDetailsService.loadUserByUsername(request.email());

        String token = jwtService.generateToken(userDetails);

        return new LoginResponse(
                token,
                "Bearer",
                "Login successful."
        );
    }

    @Override
    @Transactional(readOnly = true)
    public Admin getAdminById(UUID id) {

        return adminRepository.findById(id)
                .orElseThrow(() ->
                        new AdminNotFoundException(
                                "Admin not found with id: " + id
                        )
                );
    }

    @Override
    @Transactional(readOnly = true)
    public Admin getAdminByEmail(String email) {

        return findAdminByEmail(email);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean emailExists(String email) {

        return adminRepository.existsByEmail(email);
    }




    private void validateEmail(String email) {

        if (emailExists(email)) {
            throw new EmailAlreadyExistsException(
                    "Email already exists."
            );
        }
    }

    private Admin createAdmin(RegisterAdminRequest request) {

        Admin admin = adminMapper.toEntity(request);

        admin.setPassword(
                passwordEncoder.encode(
                        request.password()
                )
        );
        admin.setRole(UserRole.ADMIN);
        admin.setEnabled(true);
        return admin;
    }

    private Admin findAdminByEmail(String email) {

        return adminRepository.findByEmail(email)
                .orElseThrow(() ->
                        new InvalidCredentialsException(
                                "Invalid email or password."
                        )
                );
    }

    private void validatePassword(String rawPassword, String encodedPassword) {
        if (!passwordEncoder.matches(
                rawPassword,
                encodedPassword
        )) {

            throw new InvalidCredentialsException(
                    "Invalid email or password."
            );
        }
    }

}
