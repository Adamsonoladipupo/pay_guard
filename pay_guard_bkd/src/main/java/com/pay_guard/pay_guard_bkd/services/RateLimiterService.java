package com.pay_guard.pay_guard_bkd.services;

public interface RateLimiterService {
    boolean isAllowed(String ipAddress);
    void recordRequest(String ipAddress);
    void removeExpiredRequests();
}
