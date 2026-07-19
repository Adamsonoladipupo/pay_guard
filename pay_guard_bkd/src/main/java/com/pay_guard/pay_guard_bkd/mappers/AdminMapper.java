package com.pay_guard.pay_guard_bkd.mappers;

import com.pay_guard.pay_guard_bkd.data.models.Admin;
import com.pay_guard.pay_guard_bkd.dtos.requests.RegisterAdminRequest;
import com.pay_guard.pay_guard_bkd.dtos.responses.AdminResponse;
import com.pay_guard.pay_guard_bkd.dtos.responses.LoginResponse;
import com.pay_guard.pay_guard_bkd.dtos.responses.RegisterResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AdminMapper {
    Admin toEntity(RegisterAdminRequest request);
    AdminResponse toResponse(Admin admin);
//    RegisterResponse toRegisterResponse(Admin admin);
    @Mapping(target = "message", constant = "Admin registered successfully.")
    RegisterResponse toRegisterResponse(Admin admin);
    LoginResponse toLoginResponse(Admin admin);
}
