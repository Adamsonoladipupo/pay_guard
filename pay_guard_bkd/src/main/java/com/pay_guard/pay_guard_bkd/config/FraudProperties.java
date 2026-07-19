package com.pay_guard.pay_guard_bkd.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.math.BigDecimal;

@ConfigurationProperties(prefix = "payguard.fraud")
public class FraudProperties {
    private Thresholds thresholds = new Thresholds();

    private HighAmount highAmount = new HighAmount();

    private RateLimit rateLimit = new RateLimit();

    private Velocity velocity = new Velocity();

    public Thresholds getThresholds() {
        return thresholds;
    }

    public HighAmount getHighAmount() {
        return highAmount;
    }

    public RateLimit getRateLimit() {
        return rateLimit;
    }

    public Velocity getVelocity() {
        return velocity;
    }

    public static class Thresholds {

        private int approve;

        private int review;

        private int decline;

        public int getApprove() {
            return approve;
        }

        public void setApprove(int approve) {
            this.approve = approve;
        }

        public int getReview() {
            return review;
        }

        public void setReview(int review) {
            this.review = review;
        }

        public int getDecline() {
            return decline;
        }

        public void setDecline(int decline) {
            this.decline = decline;
        }
    }

    public static class HighAmount {

        private BigDecimal limit;

        public BigDecimal getLimit() {
            return limit;
        }

        public void setLimit(BigDecimal limit) {
            this.limit = limit;
        }
    }

    public static class RateLimit {

        private int maxRequests;

        private int windowMinutes;

        public int getMaxRequests() {
            return maxRequests;
        }

        public void setMaxRequests(int maxRequests) {
            this.maxRequests = maxRequests;
        }

        public int getWindowMinutes() {
            return windowMinutes;
        }

        public void setWindowMinutes(int windowMinutes) {
            this.windowMinutes = windowMinutes;
        }
    }

    public static class Velocity {

        private int maxTransactions;

        private int windowMinutes;

        public int getMaxTransactions() {
            return maxTransactions;
        }

        public void setMaxTransactions(int maxTransactions) {
            this.maxTransactions = maxTransactions;
        }

        public int getWindowMinutes() {
            return windowMinutes;
        }

        public void setWindowMinutes(int windowMinutes) {
            this.windowMinutes = windowMinutes;
        }
    }

}
