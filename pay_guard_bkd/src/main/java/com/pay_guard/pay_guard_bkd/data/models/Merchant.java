package com.pay_guard.pay_guard_bkd.data.models;

import com.pay_guard.pay_guard_bkd.data.models.emuns.MerchantCategory;
import com.pay_guard.pay_guard_bkd.data.models.emuns.MerchantStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "merchants")
@Getter
@Setter
@NoArgsConstructor
public class Merchant extends BaseEntity {

    @Column(nullable = false, unique = true, length = 50)
    private String merchantId;

    @Column(nullable = false, length = 150)
    private String merchantName;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MerchantCategory merchantCategory;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MerchantStatus status = MerchantStatus.ACTIVE;

    @OneToMany(mappedBy = "merchant")
    private List<Transaction> transactions = new ArrayList<>();
}
