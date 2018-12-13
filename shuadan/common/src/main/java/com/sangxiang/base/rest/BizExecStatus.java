package com.sangxiang.base.rest;


public enum BizExecStatus {

	
	/**
	 * OOO系统
	 * OOO△△xxx
	 * 中间三位△△预留，默认为00
	 * 后三位xxx表示错误码
	 * 后三位中xxx，001-099预留为公司统一使用的编码
	 */
	
    SUCCESS(00000000, "操作成功"),
    FAIL(-1, "操作失败");

    private final int code;
    private final String msg;

    private BizExecStatus(int val, String info) {
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
