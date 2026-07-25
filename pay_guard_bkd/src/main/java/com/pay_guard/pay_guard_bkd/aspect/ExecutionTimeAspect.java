package com.pay_guard.pay_guard_bkd.aspect;


import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ExecutionTimeAspect {
    @Around("execution(* com.pay_guard.pay_guard_bkd.services..*(..))")
    public Object measureExecutionTime(
            ProceedingJoinPoint joinPoint
    ) throws Throwable {
        long start = System.currentTimeMillis();
        String className =
                joinPoint.getTarget()
                        .getClass()
                        .getSimpleName();
        String methodName =
                joinPoint.getSignature()
                        .getName();

        try {
            return joinPoint.proceed();
        } finally {
            long executionTime =
                    System.currentTimeMillis() - start;
            System.out.println(
                    className
                            + "."
                            + methodName
                            + "() completed in "
                            + executionTime
                            + " ms"
            );
        }
    }
}
