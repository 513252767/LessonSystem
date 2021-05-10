package com.hung.util.spring.annotation;

import java.lang.annotation.*;

/**
 * @author Hung
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD,ElementType.METHOD,ElementType.PARAMETER,ElementType.ANNOTATION_TYPE})
@Documented
public @interface Value {
    String value();
}
