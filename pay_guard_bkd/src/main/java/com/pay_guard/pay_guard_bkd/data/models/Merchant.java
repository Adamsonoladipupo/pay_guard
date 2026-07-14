package com.pay_guard.pay_guard_bkd.data.models;

import jakarta.persistence.Column;

public class Merchant extends BaseEntity {

    @Column(nullable = false, unique = true, length = 50)
    private String merchantId;

    @Column(nullable = false, length = 150)
    private String merchantName;

    @Column(nullable = false, length = 100)
    private String merchantCategory;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MerchantStatus status = MerchantStatus.ACTIVE;

    @OneToMany(mappedBy = "merchant")
    private List<Transaction> transactions = new ArrayList<>();
}
