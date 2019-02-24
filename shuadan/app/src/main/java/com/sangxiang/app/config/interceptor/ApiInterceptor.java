package com.sangxiang.app.config.interceptor;

import com.alibaba.fastjson.JSON;
import com.sangxiang.app.sdk.token.UserTokenManager;
import com.sangxiang.app.utils.AppResult;
import com.sangxiang.app.utils.ISystem;
import com.sangxiang.app.utils.SpringContextUtil;
import com.sangxiang.dao.model.SysUser;
import com.sangxiang.dao.utils.TenancyContext;
import com.sangxiang.util.RedisClusterClient;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.sangxiang.dao.utils.DESUtil;

import static com.sangxiang.app.sdk.token.UserTokenManager.KEY;

/**
 * @author fujg
 */

@Slf4j
public class ApiInterceptor implements HandlerInterceptor {

    private RedisClusterClient redisClient = SpringContextUtil.getApplicationContext().getBean(RedisClusterClient.class);

    /**
     * (non-Javadoc)
     *
     * @see HandlerInterceptor#preHandle(HttpServletRequest, HttpServletResponse, Object)
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String userAgent = request.getHeader("user-agent");
        String ip = this.getIpAddr(request);
        if (null != userAgent) {
            if (userAgent.contains("Android")) {
                //log.info("userAgent=[" + userAgent + "]来至IP=[" + ip + "]的Android移动客户端请求URL[" + request.getRequestURI() + "]");
            } else if (userAgent.contains("iPhone")) {
                //log.info("userAgent=[" + userAgent + "]来至IP=[" + ip + "]的iPhone移动客户端请求URL[" + request.getRequestURI() + "]");
            } else if (userAgent.contains("iPad")) {
                //log.info("userAgent=[" + userAgent + "]来至IP=[" + ip + "]的iPad客户端请求URL[" + request.getRequestURI() + "]");
            } else {
                //log.info("userAgent=[" + userAgent + "]来至IP=[" + ip + "]的其他客户端请求URL[" + request.getRequestURI() + "]");
            }
        }
        AppResult r = new AppResult();
        try {
            String userToken = request.getHeader("userToken");
            if (StringUtils.isBlank(userToken)) {
                r.setRespCode("401");
                r.setRespMsg("invalid token");
                response.getWriter().write(JSON.toJSONString(r));
                return false;
            }
            Long userID = Long.parseLong(DESUtil.decryptStr(userToken, KEY));
            if (!redisClient.existsKey(ISystem.IUSER.USER_TOKEN + userID)) {
                r.setRespCode("401");
                r.setRespMsg("invalid token");
                response.getWriter().write(JSON.toJSONString(r));
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            r.setRespCode("401");
            r.setRespMsg("invalid token");
            response.getWriter().write(JSON.toJSONString(r));
            return false;
        }

        //long orgId = 0L;
        String userToken = request.getHeader("userToken");
        if (StringUtils.isNotBlank(userToken)) {
            //log.info(String.format("--- :::  userToken = [%s] ::: ---", userToken));
            SysUser user = UserTokenManager.getInstance().currUser(userToken);
            //orgId = user.getOrgId();
        }
        //TenancyContext.getInstance().setOrgId(orgId);
        //log.info(String.format("--- ::: in current thread = [%s] store the logined user org id = [%d] ::: --- ", Thread.currentThread().getName(), orgId));
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
        TenancyContext.getInstance().setOrgId(0L);
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
        TenancyContext.getInstance().setOrgId(0L);

    }

    public String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

}
