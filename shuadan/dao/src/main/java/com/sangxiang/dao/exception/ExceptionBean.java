package com.sangxiang.dao.exception;


import java.io.Serializable;

public class ExceptionBean implements Serializable {

    private static final long serialVersionUID = 1L;
    private int code;
    private String msg;

    private ExceptionBean(int code, String msg) {
        super();
        this.code = code;
        this.msg = msg;
    }

    public static ExceptionBean createFailResult(int code, String msg) {
        return new ExceptionBean(code, msg);
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "ExceptionBean [code=" + code + ", msg=" + msg + "]";
    }

}
