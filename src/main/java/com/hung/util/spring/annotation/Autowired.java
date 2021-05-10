package com.hung.util.spring.annotation;

import java.lang.annotation.*;

/**
 * @author Hung
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD,ElementType.FIELD,ElementType.PARAMETER,ElementType.ANNOTATION_TYPE,ElementType.CONSTRUCTOR})
@Documented
public @interface Autowired {
    boolean required() default true;
}
