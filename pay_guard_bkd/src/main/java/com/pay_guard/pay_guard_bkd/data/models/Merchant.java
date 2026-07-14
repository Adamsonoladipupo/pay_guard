package com.pay_guard.pay_guard_bkd.data.models;

import jakarta.persistence.Column;

public class Merchant extends BaseEntity {

    @Column(nullable = false, unique = true, length = 50)
    private String merchantId;

    @Column(nullable = false, length = 150)
    private String merchantName;

    
    private String merchantCategory;
    private MerchantStatus status = MerchantStatus.ACTIVE;
    private List<Transaction> transactions = new ArrayList<>();
}
