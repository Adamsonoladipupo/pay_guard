package com.pay_guard.pay_guard_bkd.annotation;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public class Audit {
    String value();
}
