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
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Aspect
@Component
public class AuditAspect {
    private static final Logger log =
            LoggerFactory.getLogger(
                    AuditAspect.class
            );
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

        String identifier = "";

        if (result instanceof RegisterResponse response) {

            identifier = response.id().toString();

        } else if (result instanceof MerchantResponse response) {

            identifier = response.merchantId();

        } else if (result instanceof TransactionResponse response) {

            identifier = response.id().toString();

        } else if (result instanceof ReviewResponse response) {

            identifier = response.flaggedTransactionId().toString();

        }


        log.info(
                """
                AUDIT
                Action      : {}
                Identifier  : {}
                Class       : {}
                Method      : {}
                Time        : {}
                """,
                audit.value(),
                identifier,
                joinPoint.getTarget().getClass().getSimpleName(),
                joinPoint.getSignature().getName(),
                LocalDateTime.now()
        );
    }
}
