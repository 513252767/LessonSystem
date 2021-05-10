package com.hung.util.spring.annotation;

import java.lang.annotation.*;

/**
 * @author Hung
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
public @interface PostConstruct {
    String value() default "";
}
