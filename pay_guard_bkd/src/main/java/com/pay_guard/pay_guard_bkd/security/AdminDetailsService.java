package com.pay_guard.pay_guard_bkd.security;

import com.pay_guard.pay_guard_bkd.data.models.Admin;
import com.pay_guard.pay_guard_bkd.data.repository.AdminRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AdminDetailsService implements UserDetailsService {

    private final AdminRepository adminRepository;

    public AdminDetailsService(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {

        Admin admin = adminRepository.findByEmail(username)
                .orElseThrow(() ->
                        new UsernameNotFoundException("Admin not found"));

        return new AdminDetails(admin);
    }
}
