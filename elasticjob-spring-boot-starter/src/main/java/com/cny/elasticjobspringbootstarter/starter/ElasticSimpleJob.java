package com.cny.elasticjobspringbootstarter.starter;

import org.springframework.stereotype.Component;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author : chennengyuan
 * 自定义注解实现
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Component
public @interface ElasticSimpleJob {
    String jobName() default "";

    String cron() default "";

    int shardingCount() default 1;

    boolean overWrite() default false;

    //是否开启事件追踪
    boolean eventTracking() default false;
}
