package com.sangxiang.app.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.concurrent.TimeUnit;

public class RedisUtil {

    /** Spring IOC 容器中的的 RedisTemplate */
    @Autowired
    private RedisTemplate IOC_Redis_Template;

    private static RedisTemplate redisTemplate;

    /** Spring 依赖注入完成之后，执行该方法 */
    @PostConstruct
    public void init() {
        redisTemplate = IOC_Redis_Template;
    }

    // --------------------------------String---------------------------------------

    public static void setValue(String key, String value) {
        redisTemplate.opsForValue().set(key, value);
    }

    public static String getValue(String key) {
        return (String) redisTemplate.opsForValue().get(key);
    }

    public static void delete(String key) {
        redisTemplate.delete(key);
    }

    /** 设置 String 类型 key-value 并添加过期时间 (毫秒单位) */
    public static void setValueWithTimeMS(String key, String value, Long time) {
        redisTemplate.opsForValue().set(key, value, time, TimeUnit.MILLISECONDS);
    }

    /** 给一个指定的 key值附加过期时间 (毫秒单位) */
    public static boolean expireValueTime(String key, Long timeout) {
        return redisTemplate.boundValueOps(key).expire(timeout, TimeUnit.MILLISECONDS);
    }

    public static Long incr(String key) {
        return redisTemplate.opsForValue().increment(key, 1);
    }

    public static Long decr(String key) {
        return redisTemplate.opsForValue().increment(key, -1);
    }

    // -----------------------------------------------------------------------

}