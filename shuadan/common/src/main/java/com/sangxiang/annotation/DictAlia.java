package com.sangxiang.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by sj on 2018/8/30.
 */
@Documented
@Inherited
@Retention(RetentionPolicy.RUNTIME)
public @interface DictAlia {
    String value() default "";
}
