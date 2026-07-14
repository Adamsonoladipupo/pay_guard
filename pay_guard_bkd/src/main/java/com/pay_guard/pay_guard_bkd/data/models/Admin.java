package com.pay_guard.pay_guard_bkd.data.models;

public class Admin extends BaseEntity {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private UserRole role;
    private boolean enabled = true;
}
