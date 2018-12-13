package com.sangxiang.util;

/**
 * Created by fujg on 2017/9/21.
 */
public class RedisKeyUtil {

    public static final String SESSION_DISTRIBUTED_SESSIONID = "session:distributed:"; //分布式session sessionid -- sessionvalue

    public static final Integer SESSION_TIMEOUT = 2; //session 失效时间2小时

    public static String getShiroSessionKey(String key){
        return "sessionid:" + key;
    }

}
