package com.sangxiang.app.component;

//import cn.jpush.api.JPushClient;
//import com.emucoo.common.msg.JiGuangProxy;
//import com.emucoo.restApi.config.JiguangConfig;
//import com.emucoo.restApi.config.QiNiuConfig;
//import com.emucoo.restApi.config.RongcloudConfig;
//import com.emucoo.restApi.config.XiaomiPushConfig;
//import com.emucoo.utils.TenancySqlInterceptor;
//import com.qiniu.util.Auth;
//import com.xiaomi.xmpush.server.Sender;
import com.qiniu.util.Auth;
import com.sangxiang.app.config.QiNiuConfig;
import com.sangxiang.dao.utils.TenancySqlInterceptor;
import org.mybatis.spring.boot.autoconfigure.ConfigurationCustomizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class BeanFactory {

    @Autowired
    private RedisConnectionFactory factory;
//    @Autowired
//    private RongcloudConfig rongcloudConfig;
    @Autowired
    private QiNiuConfig qiNiuConfig;
// @Autowired
//    private JiguangConfig jiguangConfig;
//    @Autowired
//    private XiaomiPushConfig xiaomiConfig;

    @Autowired
    private Environment env;

    @Bean("redisTemplate")
    public RedisTemplate<String,String> getRedisTemplate()  {
        RedisTemplate<String, String> template = new RedisTemplate<>();
        template.setConnectionFactory(factory);
        StringRedisSerializer keySerializer = new StringRedisSerializer();
        template.setKeySerializer(keySerializer);
        template.setHashKeySerializer(keySerializer);
        template.afterPropertiesSet();
        return template;
    }

//    @Bean("rongcloud")
//    public RongCloud getRongcloud() {
//        return RongCloud.getInstance(rongcloudConfig.getAppKey(), rongcloudConfig.getAppSecret());
//    }

    @Bean("qiniuAuth")
    public Auth getQiniuAuth() {
        return Auth.create(qiNiuConfig.getAccessKey(), qiNiuConfig.getSecretKey());
    }
//    @Bean("jpushClient")
//    public JPushClient getJPushClinet() {
//    	JPushClient jPushClient = new JPushClient(jiguangConfig.getMasterSecret(), jiguangConfig.getAppKey());
//    	return jPushClient;
//    }
//    @Bean("xiaomiSend")
//    public Sender getXiaomiSend() {
//    	Sender sender = new Sender(xiaomiConfig.getAppSecret());
//    	return sender;
//    }
//
//    @Bean
//    public JiGuangProxy createJiGuangProxy() {
//        return JiGuangProxy.createJiGuangProxyInstance(env.getProperty("jiguangPush.appKey"), env.getProperty("jiguangPush.masterSecret"), env.getProperty("jiguangPush.apnsProduction", Boolean.class), env.getProperty("jiguangPush.ignore", Boolean.class));
//    }

    @Bean
    public ConfigurationCustomizer mybatisConfigurationCustomizer() {
        return new ConfigurationCustomizer() {
            @Override
            public void customize(org.apache.ibatis.session.Configuration configuration) {
                configuration.addInterceptor(new TenancySqlInterceptor());
            }
        };
    }


}
