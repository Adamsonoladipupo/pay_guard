package com.pay_guard.pay_guard_bkd.aspect;


import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Aspect
@Component
public class ExceptionLoggingAspect {
    private static final Logger log =
            LoggerFactory.getLogger(
                    ExceptionLoggingAspect.class
            );
    @AfterThrowing(
            pointcut = "execution(* com.pay_guard.pay_guard_bkd.services..*(..))",
            throwing = "exception"
    )
    public void logException(
            JoinPoint joinPoint,
            Exception exception
    ) {

        String className =
                joinPoint.getTarget()
                        .getClass()
                        .getSimpleName();

        String methodName =
                joinPoint.getSignature()
                        .getName();

        log.error(
                "{}.{}() failed.",
                className,
                methodName,
                exception
        );
    }

}
