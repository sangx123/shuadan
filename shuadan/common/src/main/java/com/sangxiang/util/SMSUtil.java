//package com.sangxiang.util;
//
//import com.emucoo.common.config.SmsConfig;
//import com.xiaoleilu.hutool.http.HttpUtil;
//import com.xiaoleilu.hutool.log.Log;
//import com.xiaoleilu.hutool.log.LogFactory;
//
//import java.net.URLEncoder;
//import java.util.HashMap;
//import java.util.Map;
//
////import com.emucoo.common.sdk.sms.ResData;
//
////@Component
//public class SMSUtil {
//
//	protected static Log log = LogFactory.get(SMSUtil.class);
//
//	/**
//	 * 〈一句话功能简述〉
//	 *  短信网关返回格式{"result":118,"ts":"20170817163941"}
//	 * @param   [参数1] [参数1说明]
//	 * @param   [参数2] [参数2说明]
//	 * @return  [返回类型说明]
//	 *
//	 * @exception/throws [异常类型] [异常说明]
//	 * @see   [类、类#方法、类#成员](可选)
//	 */
//	public static synchronized void sendSms(String mobile,String content) throws Exception
//	{
//		SmsConfig smsConfig = SpringUtils.getBean(SmsConfig.class);
//
//		String url = smsConfig.getSmsUrl();
//		Map<String,Object> paramMap = new HashMap<String,Object>();
//		paramMap.put("account", smsConfig.getSmsAccount());
//		paramMap.put("pswd", smsConfig.getSmsPassword());
//		paramMap.put("mobile", mobile);
//		paramMap.put("msg", URLEncoder.encode(content,"UTF-8"));
//		paramMap.put("needstatus", "true");
//		paramMap.put("extno", "");
//		paramMap.put("product", "");
//		paramMap.put("resptype", "json");
//
//		String res = HttpUtil.post(url, paramMap);
//		log.info("短信网关返回=>" + res);
//
//		if(null!=res)
//		{
////			ObjectMapper mapper = new ObjectMapper(); //转换器
////			ResData resData = mapper.readValue(res, ResData.class);
////			if(resData.getResult()!=0)
////			{
////				log.info("短信发送失败！错误代码[" + resData.getResult() + "]");
////				throw new ApiException("短信发送失败！");
////			}
//		}
//	}
//
//}
