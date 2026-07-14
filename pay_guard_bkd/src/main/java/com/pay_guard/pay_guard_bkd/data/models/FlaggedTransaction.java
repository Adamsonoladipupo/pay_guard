package com.pay_guard.pay_guard_bkd.data.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "flagged_transactions")
@Getter
@Setter
@NoArgsConstructor
public class FlaggedTransaction extends BaseEntity{

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "transaction_id", nullable = false, unique = true)
    private Transaction transaction;

    
}
