package com.sangxiang.base.redis;

import com.sangxiang.util.StringUtil;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.Properties;
@Slf4j
public final class PropertiesUtil {

	//private static final Logger LOGGER = Logger.getLogger(PropertiesUtil.class);

	/** 资源属性 */
	private static Properties properties;

	/**
	 * 私有构造方法
	 */
	private PropertiesUtil() {
	}

	static {
		init();
	}
	
	public static void init() {
		if(properties == null){
			properties = new Properties();
			try {
				properties.load(PropertiesUtil.class.getClassLoader().getResourceAsStream("redis.properties"));
				//properties.load(PropertiesUtil.class.getClassLoader().getResourceAsStream("message.properties"));
				//properties.load(PropertiesUtil.class.getClassLoader().getResourceAsStream("config.properties"));
			} catch (IOException e) {
				log.error("读取配置文件出错，请确认properties文件已经放在目录下。");
			}
		}
	}
	
	/**
	 * 获取配置信息
	 * 
	 * @param key 键
	 * @return 配置信息
	 */
	public static String getValue(String key) {
		String value = properties.getProperty(key);
		if (StringUtil.isBlank(value)) {
			log.warn("没有获取指定key的值，请确认资源文件中是否存在【" + key + "】");
		}
		return value;
	}
	
	/**
	 * 获取配置信息
	 * 
	 * @param key 键
	 * @param defaultValue 当查找不到信息，则返回默认值
	 * @return 配置信息
	 */
	public static String getValue(String key,String defaultValue) {
		String value = properties.getProperty(key);
		if (StringUtil.isBlank(value)) {
			log.warn("没有获取指定key的值，请确认资源文件中是否存在【" + key + "】");
			return defaultValue;
		}
		return value;
	}
	

	/**
	 * 获取配置信息
	 * 
	 * @param key 键
	 * @param param 参数
	 * @return 配置信息
	 */
	public static String getValue(String key, Object[] param) {
		String value = getValue(key);
		return MessageFormat.format(value, param);
	}

}
