package com.pay_guard.pay_guard_bkd.services;

import com.pay_guard.pay_guard_bkd.dtos.requests.TransactionRequest;
import com.pay_guard.pay_guard_bkd.factory.FraudStrategyFactory;
import com.pay_guard.pay_guard_bkd.strategy.FraudCheckResult;
import com.pay_guard.pay_guard_bkd.strategy.FraudDetectionStrategy;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FraudDetectionService {
    private final FraudStrategyFactory factory;

    public FraudDetectionService(FraudStrategyFactory factory) {
        this.factory = factory;
    }

    public List<FraudCheckResult> analyze(TransactionRequest request) {

        List<FraudCheckResult> results = new ArrayList<>();

        for (FraudDetectionStrategy strategy : factory.getStrategies()) {

            FraudCheckResult result = strategy.check(request);

            if (result.fraudDetected()) {
                results.add(result);
            }
        }

        return results;
    }
}
