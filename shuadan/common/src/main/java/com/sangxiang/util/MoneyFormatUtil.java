package com.sangxiang.util;

import java.math.BigDecimal;

/**
 * @package: com.emucoo.common.util
 * @description: 金额单位转换工具类
 * @author: fujg
 * @date: 2017/8/9 15:43
 * @version: V1.0.0
 */
public class MoneyFormatUtil {

    //万转元
    private static final int DEFAULT_UNIT = 10000;

    public static BigDecimal toYuan(BigDecimal o){
        if(null == o){
            return null;
        }
        return o.multiply(new BigDecimal(DEFAULT_UNIT));
    }
}
