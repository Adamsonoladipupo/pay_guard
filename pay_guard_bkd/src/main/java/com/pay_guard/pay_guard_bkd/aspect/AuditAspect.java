package com.pay_guard.pay_guard_bkd.aspect;

import com.pay_guard.pay_guard_bkd.annotation.Audit;
import com.pay_guard.pay_guard_bkd.dtos.responses.MerchantResponse;
import com.pay_guard.pay_guard_bkd.dtos.responses.RegisterResponse;
import com.pay_guard.pay_guard_bkd.dtos.responses.ReviewResponse;
import com.pay_guard.pay_guard_bkd.dtos.responses.TransactionResponse;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Aspect
@Component
public class AuditAspect {
    private static final Logger log =
            LoggerFactory.getLogger(AuditAspect.class);

    @AfterReturning(
            value = "@annotation(audit)",
            returning = "result"
    )
    public void logAudit(
            JoinPoint joinPoint,
            Audit audit,
            Object result
    ) {

        String action = audit.value();

        String className =
                joinPoint.getTarget()
                        .getClass()
                        .getSimpleName();

        String methodName =
                joinPoint.getSignature()
                        .getName();

        String identifier = extractIdentifier(result);

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        String username =
                authentication != null
                        && authentication.isAuthenticated()
                        && !"anonymousUser".equals(authentication.getName())
                        ? authentication.getName()
                        : "SYSTEM";

        log.info("""
                        
                ==================== AUDIT LOG ====================
                Time        : {}
                User        : {}
                Action      : {}
                Identifier  : {}
                Class       : {}
                Method      : {}
                ================================================
                """,
                LocalDateTime.now(),
                username,
                action,
                identifier,
                className,
                methodName
        );
    }

    private String extractIdentifier(Object result) {

        if (result instanceof RegisterResponse response) {
            return response.id().toString();
        }

        if (result instanceof MerchantResponse response) {
            return response.merchantId();
        }

        if (result instanceof TransactionResponse response) {
            return response.id().toString();
        }

        if (result instanceof ReviewResponse response) {
            return response.flaggedTransactionId().toString();
        }

        return "N/A";
    }
}
