package com.pay_guard.pay_guard_bkd.aspect;

import com.pay_guard.pay_guard_bkd.annotation.Audit;
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
            value = "@annotation(audit)"
    )
    public void logAudit(
            JoinPoint joinPoint,
            Audit audit
    ) {

        String action = audit.value();

        String className =
                joinPoint.getTarget()
                        .getClass()
                        .getSimpleName();

        String methodName =
                joinPoint.getSignature()
                        .getName();

        log.info(
                "AUDIT | Time: {} | Action: {} | Class: {} | Method: {}",
                LocalDateTime.now(),
                action,
                className,
                methodName
        );
    }
}
