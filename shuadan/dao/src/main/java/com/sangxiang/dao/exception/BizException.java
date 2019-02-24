package com.sangxiang.dao.exception;


import com.sangxiang.util.ResourcesUtil;

import java.util.Locale;

public class BizException extends RuntimeException {

    private static final long serialVersionUID = 1L;
    private static final String MESSAGE_RESOURCES_NAME = "message";
    private final int code;
    private final Object[] objs;

    public BizException(int code, Object... objs) {
        this.code = code;
        this.objs = objs;
    }

    public static void throwException(int code, Object... objs) throws BizException {
        throw new BizException(code, objs);
    }

    @Override
    public String getMessage() {
        return ResourcesUtil.getValue(MESSAGE_RESOURCES_NAME, code + "", objs);
    }

    public ExceptionBean resolveError(Locale locale) {
        String message = ResourcesUtil.getValue(MESSAGE_RESOURCES_NAME, code + "", locale, objs);
        return ExceptionBean.createFailResult(code, message);
    }

}
