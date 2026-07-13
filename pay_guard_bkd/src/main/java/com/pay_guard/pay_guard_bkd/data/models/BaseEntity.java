package com.pay_guard.pay_guard_bkd.data.models;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import org.hibernate.annotations.UuidGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.util.UUID;

public abstract class BaseEntity {
    @Id
    @GeneratedValue
    @UuidGenerator
    @Column(nullable = true, updatable = true)
    private UUID Id;

    @CreatedDate
    @Column(nullable = true, updatable = true)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(nullable = false)
    private LocalDateTime updatedAt;
}
