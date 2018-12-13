package com.sangxiang.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpStatusCodeException;

/**
 * 当biz出现异常时，统一抛出
 *
 * @author fujg
 */
public class ApiSignatureException extends HttpStatusCodeException {
    
    private int reason;


    private static final long serialVersionUID = 1L;

    public ApiSignatureException(HttpStatus statusCode, String msg) {
        super(statusCode,msg);
        
    }
    
    public ApiSignatureException(HttpStatus statusCode, int reason, String msg) {
        super(statusCode,msg);
        this.reason=reason;
    }

    public int getReason() {
        return reason;
    }


}