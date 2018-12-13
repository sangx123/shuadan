//package com.sangxiang.util;
//
//import cn.jiguang.common.resp.APIConnectionException;
//import cn.jiguang.common.resp.APIRequestException;
//import cn.jiguang.common.resp.BaseResult;
//import cn.jpush.api.JPushClient;
//import cn.jpush.api.push.model.Options;
//import cn.jpush.api.push.model.Platform;
//import cn.jpush.api.push.model.PushPayload;
//import cn.jpush.api.push.model.audience.Audience;
//import cn.jpush.api.push.model.notification.AndroidNotification;
//import cn.jpush.api.push.model.notification.IosAlert;
//import cn.jpush.api.push.model.notification.IosNotification;
//import cn.jpush.api.push.model.notification.Notification;
//import cn.jpush.api.push.model.notification.Notification.Builder;
//import com.xiaomi.xmpush.server.Constants;
//import com.xiaomi.xmpush.server.Message;
//import com.xiaomi.xmpush.server.Sender;
//import org.apache.commons.lang3.StringUtils;
//import org.json.simple.parser.ParseException;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.core.env.Environment;
//
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Map;
//import java.util.Map.Entry;
//
//@Deprecated
//public class MsgPushing {
//
//	private static Logger logger = LoggerFactory.getLogger(MsgPushing.class);
//
//	private JPushClient jPushClient;
//	private boolean jiguangApnsProduct;
//	private Sender xiaomiSender;
//	private boolean xiaomiIsDebug;
//	private String xiaomiPackageName;
//
//	public MsgPushing(Environment env) {
//		jPushClient = new JPushClient(env.getProperty("jiguangPush.masterSecret"), env.getProperty("jiguangPush.appKey"));
//		xiaomiSender = new Sender(env.getProperty("xiaomiPush.appSecret"));
//		xiaomiIsDebug = Boolean.getBoolean(env.getProperty("xiaomiPush.isDebug"));
//		jiguangApnsProduct = Boolean.getBoolean(env.getProperty("jiguangPush.apnsProduction"));
//		xiaomiPackageName = env.getProperty("xiaomiPush.packageName");
//	}
//
//	public int pushMessage(String title, String content, Map<String, String> extra, List<String> pushTokens) {
//		if (pushTokens.isEmpty()) {
//			return 0;
//		}
//		List<String> jpush = new ArrayList<>();
//		List<String> huawei = new ArrayList<>();
//		List<String> xiaomi = new ArrayList<>();
//		for (String pushToken : pushTokens) {
//			if (StringUtils.isBlank(pushToken)) {
//				continue;
//			}
//			String[] split = pushToken.split(":");
//			if (split.length == 1 || StringUtils.equalsAny(split[1], "", "jpush")) {
//				jpush.add(split[0]);
//				continue;
//			}
//			if (StringUtils.equals(split[1], "huawei")) {
//				huawei.add(split[0]);
//				continue;
//			}
//			if (StringUtils.equals(split[1], "xiaomi")) {
//				xiaomi.add(split[0]);
//				continue;
//			}
//		}
//		// JPUSH推送
//		int jpushSuccess = pushJpushMsg(title, content, extra, jpush);
//		// 小米推送
//		int xiaomiSuccess = pushXiaomiMsg(title, content, extra, xiaomi);
//		return jpushSuccess + xiaomiSuccess;
//	}
//
//	private int pushJpushMsg(String title, String content, Map<String, String> extra, List<String> jpush) {
//		if (jpush.isEmpty()) {
//			return 0;
//		}
//		// 设置APNS是否为生产环境
//		Options sendno = Options.sendno();
//		sendno.setApnsProduction(jiguangApnsProduct);
//
//		// 构造额外参数
//		Builder builder = Notification.newBuilder();
//		addNotification(title, content, builder, extra);
//
//		/*
//		 * 自定义消息声音参数
//		 */
//		//如果传递了声音设置自定义声音
//		String sound = extra.get("sound");
//		if(StringUtils.isBlank(sound)){
//			extra.put("sound", "default");
//		}
//		cn.jpush.api.push.model.Message message=cn.jpush.api.push.model.Message.newBuilder()
//				.setTitle(title)
//				.setMsgContent(content)
//				.addExtras(extra).build();
//
//		PushPayload payload = PushPayload.newBuilder().setPlatform(Platform.all()).setAudience(Audience.registrationId(jpush))
//				.setNotification(builder.build()).setOptions(sendno)
//				.setMessage(message)
//				.build();
//
//		BaseResult sendPush;
//		try {
//			sendPush = jPushClient.sendPush(payload);
//			if (sendPush.isResultOK()) {
//				logger.info("Jpush消息推送成功{}", payload.toString());
//				return jpush.size();
//			}
//			logger.warn("Jpush消息推送失败{}", sendPush.toString());
//		} catch (APIConnectionException | APIRequestException e) {
//			logger.warn("Jpush消息推送失败{}", e.getMessage());
//		}
//		return 0;
//	}
//
//	private void addNotification(String title, String content, Builder builder, Map<String, String> extra) {
//		AndroidNotification.Builder androidBuilde = AndroidNotification.newBuilder().addExtras(extra).setAlert(content).setTitle(title);
//		IosNotification.Builder IosBuilder = IosNotification.newBuilder().addExtras(extra).setBadge(1)
//				.setAlert(IosAlert.newBuilder().setTitleAndBody(title, null, content).build());
//
//		//如果传递了声音设置自定义声音
//		String sound = extra.get("sound");
//		if(StringUtils.isNotBlank(sound)){
//			IosBuilder.setSound(sound);
//		}else{
//			IosBuilder.setSound("default");
//		}
//
//		builder.addPlatformNotification(androidBuilde.build()).addPlatformNotification(IosBuilder.build());
//	}
//
//	private int pushXiaomiMsg(String title, String content, Map<String, String> extra, List<String> xiaomi) {
//		if (xiaomi.isEmpty()) {
//			return 0;
//		}
//		if (xiaomiIsDebug) {
//			Constants.useSandbox();
//		} else {
//			Constants.useOfficial();
//		}
//		Message.Builder builder = new Message.Builder();
//		builder.title(title).description(content).passThrough(0).restrictedPackageName(xiaomiPackageName);
//		for (Entry<String, String> entry : extra.entrySet()) {
//			builder.extra(entry.getKey(), entry.getValue());
//		}
//		try {
//			xiaomiSender.send(builder.build(), xiaomi, 1);// 重试1次
//			return xiaomi.size();
//		} catch (IOException | ParseException e) {
//			logger.warn("消息推送失败{}", e.getMessage());
//			return 0;
//		}
//	}
//}
