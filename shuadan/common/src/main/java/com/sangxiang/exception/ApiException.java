package com.sangxiang.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class ApiException extends RuntimeException{

    /**
	 * serialVersionUID:TODO（描述这个变量表示什么）
	 *
	 */
	
	private static final long serialVersionUID = 1L;
	private int code = 500;
    private String msg = "";
    
    public ApiException(Throwable e) {
    	super(e);
    }
    public ApiException() {

    }

    public ApiException(String msgs) {
        super(msgs);
        this.msg = msgs;
    }

    public ApiException(int code, String msgs) {
        super(msgs);
        this.code = code;
        this.msg = msgs;
    }

    public ApiException(int code, String msgs, Throwable e) {
        super(msgs, e);
        this.code = code;
        this.msg = msgs;
    }
}
