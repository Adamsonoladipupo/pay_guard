package com.pay_guard.pay_guard_bkd.data.models;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import org.hibernate.annotations.UuidGenerator;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;
import java.util.UUID;

public abstract class BaseEntity {
    @Id
    @GeneratedValue
    @UuidGenerator
    @Column(nullable = true, updatable = true)
    private UUID Id;

    @CreatedDate
    @
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
