package com.sangxiang.app;

import com.sangxiang.DateEditor;
import com.sangxiang.StringEditor;
import com.sangxiang.app.sdk.token.ReSubmitTokenManager;
import com.sangxiang.app.utils.AppResult;
import com.sangxiang.exception.ApiException;
import com.sangxiang.exception.ApiNotLoginException;
import com.sangxiang.exception.ApiReSubmitException;
import com.sangxiang.util.WebUtil;
import com.xiaoleilu.hutool.util.StrUtil;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/**
 * @author Created by JK on 2017/3/10.
 */
public class AppBaseController extends AppBase {

    @Resource
    public HttpServletRequest request;

    protected final transient String API_URL = "http://localhost/api/v1";

//    String source = (String) request.getSession().getAttribute("source");

    private void rollBackSubmitToken() {
        if (StrUtil.isNotEmpty(submitToken)) {
            ReSubmitTokenManager.rollBackToken(submitToken);
        }
    }

    /**
     * 默认页为1
     */
    protected static final Integer PAGENUM = 1;
    /**
     * 页码大小10
     */
    protected static final Integer PAGESIZE = 10;

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
        binder.registerCustomEditor(Date.class, new DateEditor(true));
        binder.registerCustomEditor(String.class, "password", new StringEditor(true));
    }

    public <T> AppResult<T> makeResult(String code, String msg, T res) {
        return new AppResult<T>(code, msg, res);
    }

    public <T> AppResult<T> makeResult(String code, String msg) {
        return new AppResult<T>(code, msg);
    }

    public <T> AppResult<T> makeResult(AppExecStatus status, String detail) {
        return new AppResult<T>(status.getCode(), status.getMsg() + "：" + detail, null);

    }

    public <T> AppResult<T> success(T res) {
        return this.makeResult(AppExecStatus.SUCCESS.getCode(), AppExecStatus.SUCCESS.getMsg(), res);
    }

    public <T> AppResult<T> fail(AppExecStatus error, String detail) {

        return this.makeResult(error, detail);
    }


    @ExceptionHandler
    @ResponseBody
    public AppResult exceptionHandler(Exception ex,
                                      HttpServletRequest request,
                                      HttpServletResponse response) throws Exception {

        ex.printStackTrace();
        log.info(ex);
        if (WebUtil.isAjaxRequest(request)) {
            if (ex instanceof ApiReSubmitException) {
                return AppResult.reSubmit("操作失败：请勿重复提交或防重复提交异常！");
            } else if (ex instanceof ApiNotLoginException) {
                rollBackSubmitToken();
                return AppResult.notLogin("操作失败：用户未登录！");
            } else if (ex instanceof ApiException) {
                rollBackSubmitToken();
                return AppResult.busErrorRes("操作失败：" + ex.getMessage());
            } else if (ex instanceof HttpMessageNotReadableException) {
                rollBackSubmitToken();
                return AppResult.paramErrorRes("操作失败：提交参数错误！");
            } else {
                rollBackSubmitToken();
                return AppResult.sysErrorRes("操作失败：系统繁忙，请稍后再试！");
            }
        } else {
            if (ex instanceof ApiReSubmitException) {
                return AppResult.reSubmit("操作失败：请勿重复提交或防重复提交异常！");
            } else if (ex instanceof ApiNotLoginException) {
                rollBackSubmitToken();
                return AppResult.notLogin("操作失败：用户未登录！");
            } else if (ex instanceof ApiException) {
                rollBackSubmitToken();
                return AppResult.busErrorRes("操作失败：" + ex.getMessage());
            } else if (ex instanceof HttpMessageNotReadableException) {
                rollBackSubmitToken();
                return AppResult.paramErrorRes("操作失败：提交参数错误！");
            } else if (ex instanceof MissingServletRequestParameterException) {
                return AppResult.paramErrorRes("缺少参数:" + ((MissingServletRequestParameterException) ex).getParameterName());
            } else {
                rollBackSubmitToken();
                return AppResult.sysErrorRes("操作失败：系统繁忙，请稍后再试！");
            }
        }
    }

}
