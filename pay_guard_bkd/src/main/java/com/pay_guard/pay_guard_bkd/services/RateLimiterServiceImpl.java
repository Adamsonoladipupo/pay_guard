package com.pay_guard.pay_guard_bkd.services;

import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class RateLimiterServiceImpl implements RateLimiterService{
    private static final int MAX_REQUESTS = 5;

    private static final Duration WINDOW =
            Duration.ofMinutes(1);

    private final ConcurrentHashMap<String, Deque<Instant>>
            requestStore = new ConcurrentHashMap<>();

    @Override
    public boolean isAllowed(String ipAddress) {

        Instant now = Instant.now();

        Deque<Instant> requests =
                requestStore.computeIfAbsent(
                        ipAddress,
                        key -> new ArrayDeque<>()
                );

        synchronized (requests) {

            while (!requests.isEmpty()
                    &&
                    requests.peekFirst()
                            .isBefore(now.minus(WINDOW))) {

                requests.pollFirst();

            }

            if (requests.size() >= MAX_REQUESTS) {

                return false;

            }

            requests.addLast(now);

            return true;
        }
    }
}
