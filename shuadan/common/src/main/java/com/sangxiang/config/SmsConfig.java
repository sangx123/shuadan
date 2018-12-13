package com.sangxiang.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * 〈一句话功能简述〉
 * 〈功能详细描述〉
 *
 * @author  fujg
 * @see     [相关类/方法]（可选）
 * @version TODOVer 1.1
 * @Date	 2017 2017年8月14日 上午9:51:09
 *
 */
@ConfigurationProperties(prefix="sdk")
@PropertySource("classpath:config/sms.properties")
@Component
@Data
public class SmsConfig {
	
	private String smsSwitch;
	private String smsUrl;
	private String smsCustid;
	private String smsAccount;
	private String smsPassword;
	private String smsMessageTitle;
	private String smsMessageTime;
	private String smsRegister;
	private String smsCheck;
	
	/**
	 * 短信应答XML--Node--returnstatus 短信应答XML--Node--message
	 * 短信应答XML--Node--remainpoint 短信应答XML--Node--taskID
	 * 短信应答XML--Node--successCounts
	 */
	public static final String RETURNSTATUS = "returnstatus";
	public static final String MESSAGE = "message";
	public static final String REMAINPOINT = "remainpoint";
	public static final String TASKID = "taskID";
	public static final String SUCCESSCOUNTS = "successCounts";

	public static final String SUCCESS = "Success";

	/**
	 * 短信发送状态
	 * 
	 * @author fujg
	 * @version 1.0
	 * @created 2016-04-29
	 */
	public class SendStatus {
		// 0:初始
		public static final int TYPE0 = 0;
		// 1:发送中
		public static final int TYPE1 = 1;
		// 2:发送完成
		public static final int TYPE2 = 2;
	}

	/**
	 * 短信发送渠道
	 * 
	 * @author fujg
	 * @version 1.0
	 * @created 2016-04-29
	 */
	public class SMSChannel {
		// 诺炫短信
		public static final String SMS001 = "NUOXUAN";
		public static final String SMS002 = "";
		public static final String SMS003 = "";

	}
}
