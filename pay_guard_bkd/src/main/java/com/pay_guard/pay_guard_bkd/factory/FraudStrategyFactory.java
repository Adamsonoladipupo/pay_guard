package com.pay_guard.pay_guard_bkd.factory;

import com.pay_guard.pay_guard_bkd.strategy.FraudDetectionStrategy;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class FraudStrategyFactory {
    private final List<FraudDetectionStrategy> strategies;
    public FraudStrategyFactory(
            List<FraudDetectionStrategy> strategies) {
        this.strategies = strategies;
    }
    public List<FraudDetectionStrategy> getStrategies() {
        return strategies;
    }
}
