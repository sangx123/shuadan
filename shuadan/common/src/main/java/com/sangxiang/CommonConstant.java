package com.sangxiang;

public class CommonConstant {
	
	public static final String SESSION_DISTRIBUTED_SESSIONID = "session:distributed:"; //分布式session sessionid -- sessionvalue
    public static final String JSESSIONID = "5645645wssss5s5s4s8s4a1ds52d1d1d4f2d"; //分布式session sessionid -- sessionvalue

    public static final Integer SESSION_TIMEOUT = 2; //session 失效时间2小时
    
    /**
	 * 表单token
	 */
	public static final String TOKEN_FORM = "tokenForm";

	/**
	 * 刷新表单token
	 */
	public static final String HEAD_REFRESH_TOKEN_FORM = "X-Refresh-Token-Form";

	/**
	 * Ajax操作没有权限的响应头key
	 */
	public static final String HEAD_NO_PERMISSION_KEY = "X-No-Permission";

	/**
	 * Ajax操作没有权限的响应头value
	 */
	public static final String HEAD_NO_PERMISSION_VALUE = "No-Permission";

	/**
	 * Ajax操作登陆超时的响应头key
	 */
	public static final String HEAD_SESSION_STATUS_KEY = "X-Session-Status";

	/**
	 * Ajax操作登陆超时的响应头value
	 */
	public static final String HEAD_SESSION_STATUS_VALUE = "Session-Timeout";
	
	


}
