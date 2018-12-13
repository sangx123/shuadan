package com.sangxiang.util;

import com.xiaoleilu.hutool.date.DateUtil;

import java.util.Date;

/**
 * @package: com.emucoo.common.util
 * @description: 产品编号生产工具
 * @author: fujg
 * @date: 2017/8/28 14:19
 * @version: V1.0.0
 */
public class ReserveCodeUtil {

    private volatile static ReserveCodeUtil RESERVE_CODE_UTIL;

    public static synchronized ReserveCodeUtil getInstance(){
        if(null == RESERVE_CODE_UTIL){
            synchronized(ReserveCodeUtil.class){
                if(null == RESERVE_CODE_UTIL){
                	RESERVE_CODE_UTIL = new ReserveCodeUtil();
                }
            }
        }
        return RESERVE_CODE_UTIL;
    }

    private ReserveCodeUtil(){}

    /**
     * 获取私募预约编号
     * @return
     */
    public static String getPriPrdCode(){
        return "PR" + getInstance().generatorCode();
    }

    /**
     * 获取内固预约编号
     * @return
     */
    public static String getFixPrdCode(){
        return "FR" + getInstance().generatorCode();
    }

    /**
     * 产生随机编号
     * @return
     */
    private String generatorCode(){
        Date date = new Date();
        return DateUtil.format(date, "yyyyMMddHHmmssSSS");
    }
}
