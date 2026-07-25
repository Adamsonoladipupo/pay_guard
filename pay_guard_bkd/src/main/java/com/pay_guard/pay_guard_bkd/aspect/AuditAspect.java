package com.pay_guard.pay_guard_bkd.aspect;

import com.pay_guard.pay_guard_bkd.annotation.Audit;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class AuditAspect {
    private static final Logger log =
            LoggerFactory.getLogger(
                    AuditAspect.class
            );
    
}
