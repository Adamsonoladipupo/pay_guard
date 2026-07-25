package com.pay_guard.pay_guard_bkd.aspect;


import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ExceptionLoggingAspect {
    private static final Logger log =
            LoggerFactory.getLogger(
                    ExceptionLoggingAspect.class
            );
    
}
