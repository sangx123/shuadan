package com.sangxiang.web.oauth2;

import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Shiro配置
 */
@Configuration
@ComponentScan
public class ShiroConfig {

    @Bean("sessionManager")
    public SessionManager sessionManager(){
        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
        sessionManager.setSessionValidationSchedulerEnabled(true);
        sessionManager.setSessionIdUrlRewritingEnabled(false);
        return sessionManager;
    }

    @Bean("securityManager")
    public SecurityManager securityManager(OAuth2Realm oAuth2Realm, SessionManager sessionManager) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(oAuth2Realm);
        securityManager.setSessionManager(sessionManager);
        return securityManager;
    }

    @Bean("shiroFilter")
    public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager) {
        //1，必须设置securityManager
        ShiroFilterFactoryBean shiroFilter = new ShiroFilterFactoryBean();
        shiroFilter.setSecurityManager(securityManager);
        //oauth过滤，实现自己的过滤规则
        Map<String, Filter> filters = new HashMap<>();
        filters.put("oauth2", new OAuth2Filter());
        shiroFilter.setFilters(filters);

        Map<String, String> filterMap = new LinkedHashMap<>();
        // 配置不会被拦截的链接 顺序判断
        //登陆，注册，忘记密码 可以匿名访问
        filterMap.put("/login", "anon");
        filterMap.put("/register", "anon");
         //filterMap.put("/applyTask", "anon");
        filterMap.put("/test", "anon");
        filterMap.put("/find-password", "anon");
        //阿里支付不用拦截
        filterMap.put("/alipay", "anon");
       //filterMap.put("/checkAlipay", "anon");
        filterMap.put("/", "anon");
        //需要已认证
        //filterMap.put("/**", "oauth2");

        // 如果不设置默认会自动寻找Web工程根目录下的"/login.jsp"页面
//        shiroFilter.setLoginUrl("/login");
//        // 登录成功后要跳转的链接
//        shiroFilter.setSuccessUrl("/index");
//        // 未授权界面
//        shiroFilter.setUnauthorizedUrl("/errorView/403_error.html");//不生效(详情原因看MyExceptionResolver)
        shiroFilter.setFilterChainDefinitionMap(filterMap);



        return shiroFilter;
    }

    @Bean("lifecycleBeanPostProcessor")
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    /**
     * 解决spring aop和注解配置一起使用的bug。如果您在使用shiro注解配置的同时，引入了spring aop的starter，会有一个奇怪的问题，导致shiro注解的请求，
     * 不能被映射，需加入以下配置：
     * @return
     */
    @Bean
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator proxyCreator = new DefaultAdvisorAutoProxyCreator();
        proxyCreator.setProxyTargetClass(true);
        return proxyCreator;
    }

    /**
     * 开启shiro aop注解支持 否则doGetAuthorizationInfo()不会执行
     * @param securityManager
     * @return
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
        advisor.setSecurityManager(securityManager);
        return advisor;
    }
}
