package com.sangxiang.annotation;

import java.lang.annotation.*;

/**
 * 定义aop annotation切入点凡是方法上标注了@OperationLog的则会自动记录操作日志和异常日志
 */
@Target({ElementType.PARAMETER, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface OperationLog {
    /**
     * 日志操作描述
     * @return
     */
    String value()  default "";
    /**
     *
     * moduleName:(模块名称). <br/>
     *
     * @author fujg
     * @return
     */
    String moduleName() default "";

    /**
     *
     * description:(描述). <br/>
     *
     * @author fujg
     * @return
     */
    String description() default "";
}