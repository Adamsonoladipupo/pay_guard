package com.pay_guard.pay_guard_bkd.services;

import com.pay_guard.pay_guard_bkd.config.FraudProperties;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class RateLimiterServiceImpl implements RateLimiterService{

    private final FraudProperties properties;
    public RateLimiterServiceImpl(FraudProperties properties) {
        this.properties = properties;
    }

    private final ConcurrentHashMap<String, Deque<Instant>>
            requestStore = new ConcurrentHashMap<>();

    @Override
    public boolean isAllowed(String ipAddress) {

        Instant now = Instant.now();
        Deque<Instant> requests =
                requestStore.computeIfAbsent(
                        ipAddress, key -> new ArrayDeque<>());

        synchronized (requests) {

            while (!requests.isEmpty()
                    &&
                    requests.peekFirst()
                            .isBefore(now.minus(Duration.ofMinutes(
                                    properties.getRateLimit().getWindowMinutes()
                            )))) {

                requests.pollFirst();

            }

            if (requests.size() >= properties.getRateLimit().getMaxRequests()) {

                return false;

            }

            requests.addLast(now);

            return true;
        }
    }
}
