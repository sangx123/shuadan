package com.sangxiang.base.rest;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * Rest api 执行状态描述
 */
@JsonInclude(Include.ALWAYS)
public class ApiResult<T> {

    /**
     * 操作执行情况代码，0-表示成功，其他表示失败
     */
    private final int code;

    /**
     * 执行情况描述
     */
    private final String msg;

    private final T result;

    public ApiResult(int code, String msg) {
        this(code, msg, null);

    }

    public ApiResult(int code, String msg, T res) {
        this.code = code;
        this.msg = msg;

        this.result = res;

    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public T getResult() {
        return result;
    }

}
