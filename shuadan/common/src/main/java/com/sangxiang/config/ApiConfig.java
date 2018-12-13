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
@ConfigurationProperties(prefix="api")
@PropertySource("classpath:config/api.properties")
@Component
@Data
public class ApiConfig {
	
	private String fileUrl;
	private String filePath;

}
