package com.sangxiang.exception;


import com.sangxiang.ExecStatus;

/**
 * @package: com.emucoo.common.exception
 * @description: 业务逻辑异常
 * @version: V1.0.0
 */
public class ServiceException extends BaseException{
    public ServiceException(String msg) {
        super(ExecStatus.FAIL.getCode(), msg);
    }

    public ServiceException(int code, String msg) {
        super(code, msg);
    }
}
