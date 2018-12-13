package com.sangxiang.base.rest;

/**
 * Rest api 执行状态定义
 *
 */
public enum ApiExecStatus {

    SUCCESS(0, "操作成功"),
    FAIL(-1, "操作失败"),
    //-----参数问题
    INVALID_PARAM(1000, "参数不合法"),
    //-----数据验证问题

     DAO_ERR(6000, "数据访问失败！"),
     
    //-----系统异常
    SYSTEM_ERR(10000, "系统异常，请联系管理员");

    private final int code;
    private final String msg;

    private ApiExecStatus(int val, String info) {
        this.code = val;
        this.msg = info;

    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
