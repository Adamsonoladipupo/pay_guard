package com.pay_guard.pay_guard_bkd.data.models;

import java.time.LocalDateTime;
import java.util.UUID;

public abstract class BaseEntity {
    private UUID Id;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
