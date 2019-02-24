package com.sangxiang.app;

public enum AppExecStatus {

	
	SUCCESS("000", "操作成功"),
    FAIL("-1", "操作失败"),
    //-----参数问题
    INVALID_PARAM("501", "参数不合法"),
    
    //-----数据验证问题
    BIZ_ERROR("400", "业务错误"),
    
    SERVICE_ERR("300", "服务失败"),
     
    //-----系统异常
    SYSTEM_ERR("500", "系统异常");
	
	
	private final String code;
    private final String msg;
    
    
    private AppExecStatus(String val, String info) {
        this.code = val;
        this.msg = info;

    }

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
