package com.sangxiang.base.rest;

public class BaseResource {

    /**
     * 生成api执行结果的通用方法
     *
     * @param <T> - 结果类型
     * @param code - api执行结果代码，不能为空
     * @param msg - 结果描述
     * @param res - 结果数据，可以为空
     * @return
     */
    /**
     * 默认页为1
     */
    protected static final Integer PAGENUM = 1;
    /**
     * 页码大小10
     */
    protected static final Integer PAGESIZE = 10;

    public <T> ApiResult<T> makeResult(int code, String msg, T res) {
        return new ApiResult<T>(code, msg, res);

    }

    public <T> ApiResult<T> makeResult(ApiExecStatus status, T res) {
        return new ApiResult<T>(status.getCode(), status.getMsg(), res);

    }

    public <T> ApiResult<T> makeResult(ApiExecStatus status, String detail) {
        return new ApiResult<T>(status.getCode(), status.getMsg() + "：" + detail, null);

    }

    public <T> ApiResult<T> success(String msg, T res) {

        return this.makeResult(ApiExecStatus.SUCCESS.getCode(), msg, res);
    }

    public <T> ApiResult<T> success(T res) {

        return this.makeResult(ApiExecStatus.SUCCESS, res);
    }

    /**
     *
     * @param error
     * @return
     */
    public <T> ApiResult<T> fail(ApiExecStatus error) {

        return this.makeResult(error, null);
    }

    public <T> ApiResult<T> fail(ApiExecStatus error, String detail) {

        return this.makeResult(error, detail);
    }

    public <T> ApiResult<T> fail(int code, String msg) {

        return this.makeResult(code, msg, null);
    }
    
    public <T> ApiResult<T> fail(int code, String msg,T res) {
        
        return this.makeResult(code, msg, res);
    }

    public <T> ApiResult<T> fail(String msg) {

        return this.makeResult(ApiExecStatus.FAIL.getCode(), msg, null);
    }

    public <T> ApiResult<T> fail() {

        return this.makeResult(ApiExecStatus.FAIL, null);
    }

   
}
