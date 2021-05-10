package com.hung.util.spring.annotation;

import java.lang.annotation.*;

/**
 * @author Hung
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
public @interface ComponentScan {
    String value() default "";
}
