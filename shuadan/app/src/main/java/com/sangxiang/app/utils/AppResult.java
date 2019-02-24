package com.sangxiang.app.utils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sangxiang.exception.ApiException;
import com.xiaoleilu.hutool.log.Log;
import com.xiaoleilu.hutool.log.LogFactory;

/**
 * 封装返回数据
 * 
 */
@SuppressWarnings("rawtypes")
public class AppResult<T> extends ParamVo<T> implements Result{
	
	protected final transient Log log = LogFactory.get(this.getClass());

	private void init()
	{
		setCmd("cmd");
		setVersion(1000L);
		setSign("emucoo");
		setPageType("1");
	}
	
	@SuppressWarnings("unchecked")
	public AppResult(String retcode, String retmsg, T data){
		init();
		setRespCode(retcode);
		setRespMsg(retmsg);
		setData(data);
		printLog();
	}
	
	public AppResult(String retcode, String retmsg){
		init();
		setRespCode(retcode);
		setRespMsg(retmsg);
		printLog();
	}
	
	@SuppressWarnings("unchecked")
	public AppResult(T data){
		init();
		setRespCode(success_code);
		setRespMsg("查询成功");
		setData(data);
		printLog();
	}
	
	public AppResult(String retcode){
		init();
		setRespCode(retcode);
		setRespMsg("操作失败");
		printLog();
	}
	
	public AppResult(){
	}

	@Override
	public String toString() {
		return "AjaxResult [retcode=" + getRespCode() + ", retmsg=" + getRespMsg() + ", data=" + getData() + "]";
	}
	
	public void printLog()
	{
		ObjectMapper mapper = new ObjectMapper();
    	try 
    	{
			log.info("返回参数[" + mapper.writeValueAsString(this) + "]");
		} 
    	catch (JsonProcessingException e) 
    	{
			e.printStackTrace();
			throw new ApiException(e.getMessage());
		}
	}
	
	@SuppressWarnings("unchecked")
	public static AppResult successRes(String msg, Object obj){
		return new AppResult(success_code,msg, obj);
	}
	@SuppressWarnings("unchecked")
	public static AppResult successRes(String msg){
		return new AppResult(success_code, msg, null);
	}
	@SuppressWarnings("unchecked")
	public static AppResult notLoginSuccessRes(String msg, Object obj){
		return new AppResult(not_login,msg, obj);
	}
	@SuppressWarnings("unchecked")
	public static AppResult sysErrorRes(String msg){
		return new AppResult(sys_error_code, msg, null);
	}
	@SuppressWarnings("unchecked")
	public static AppResult reSubmit(String msg){
		return new AppResult(re_submit, msg, null);
	}
	@SuppressWarnings("unchecked")
	public static AppResult notLogin(String msg){
		return new AppResult(not_login, msg, null);
	}
	@SuppressWarnings("unchecked")
	public static AppResult notLogin(){
		return new AppResult(not_login, "用户未登录!", null);
	}
	@SuppressWarnings("unchecked")
	public static AppResult paramErrorRes(String msg) {
		return new AppResult(params_error_code, msg, null);
	}
	@SuppressWarnings("unchecked")
	public static AppResult huifuErrorRes(String msg) {
		return new AppResult(huifu_error_code, msg, null);
	}
	@SuppressWarnings("unchecked")
	public static AppResult busErrorRes(String msg){
		return new AppResult(bus_error_code, msg, null);
	}
}
