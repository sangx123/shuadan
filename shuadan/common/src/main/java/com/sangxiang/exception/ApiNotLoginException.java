package com.sangxiang.exception;

public class ApiNotLoginException extends ApiException {
	
	private static final int NOT_LOGIN_CODE = 300;

	public ApiNotLoginException()
	{
		super(NOT_LOGIN_CODE, "用户未登录！");
	}

	/**
	 * serialVersionUID:TODO（描述这个变量表示什么）
	 *
	 */
	
	private static final long serialVersionUID = 1L;

}
