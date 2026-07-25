package com.pay_guard.pay_guard_bkd.services;

import com.pay_guard.pay_guard_bkd.config.FraudProperties;
import com.pay_guard.pay_guard_bkd.data.models.emuns.TransactionStatus;
import com.pay_guard.pay_guard_bkd.dtos.requests.TransactionRequest;
import com.pay_guard.pay_guard_bkd.factory.FraudStrategyFactory;
import com.pay_guard.pay_guard_bkd.services.model.FraudAnalysisResult;
import com.pay_guard.pay_guard_bkd.strategy.FraudCheckResult;
import com.pay_guard.pay_guard_bkd.strategy.FraudDetectionStrategy;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FraudDetectionService {

    private final FraudStrategyFactory factory;
    private final FraudProperties properties;

    public FraudDetectionService(FraudStrategyFactory factory, FraudProperties properties) {
        this.factory = factory;
        this.properties = properties;
    }

    public FraudAnalysisResult analyze(TransactionRequest request) {
        List<FraudCheckResult> results = new ArrayList<>();
        int totalRiskScore = 0;
        for (FraudDetectionStrategy strategy : factory.getStrategies()) {
            FraudCheckResult result = strategy.check(request);
            if (result.fraudDetected()) {
                results.add(result);
                totalRiskScore += result.riskScore();
            }
        }
        boolean fraudDetected = !results.isEmpty();
        TransactionStatus status =
                determineTransactionStatus(totalRiskScore);

        return new FraudAnalysisResult(
                fraudDetected,
                totalRiskScore,
                status,
                results
        );
    }

    private TransactionStatus determineTransactionStatus(int score) {

        if (score >= properties.getThresholds().getDecline()) {
            return TransactionStatus.DECLINED;
        }
        if (score >= properties.getThresholds().getReview()) {
            return TransactionStatus.FLAGGED;
        }
        return TransactionStatus.APPROVED;
    }
}
