package com.sangxiang.app.oauth2;
import com.sangxiang.util.R;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.web.filter.authc.AuthenticatingFilter;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import com.google.gson.Gson;
/**
 * oauth2过滤器
 *
 */
@Slf4j
public class OAuth2Filter extends AuthenticatingFilter {

    @Override
    protected AuthenticationToken createToken(ServletRequest request, ServletResponse response) throws Exception {
        //获取请求token
        String token = getRequestToken((HttpServletRequest) request);

        if(StringUtils.isBlank(token)){
            return null;
        }

        return new OAuth2Token(token);
    }

    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        return false;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        //获取请求token，如果token不存在，直接返回401
        String token = getRequestToken((HttpServletRequest) request);
        if(StringUtils.isBlank(token)){
            return returnResultJson((HttpServletResponse) response);
        }else{
            String requestUri= ((HttpServletRequest) request).getRequestURI();
            if(requestUri.startsWith("/sys/org/") || requestUri.startsWith("/sys/version/") ){
                WebApplicationContext wac = WebApplicationContextUtils.getRequiredWebApplicationContext(request.getServletContext());
                //SysUserTokenService sysUserTokenService =(SysUserTokenService)wac.getBean("sysUserTokenServiceImpl");
                //SysUserToken sysUserToken=sysUserTokenService.queryByToken(token);
                //if(!sysUserToken.getId().equals(1L)){
                    return returnResultJson((HttpServletResponse) response);
                //}
            }
        }

        return executeLogin(request, response);
    }

    private boolean returnResultJson(HttpServletResponse response) throws IOException {
        HttpServletResponse httpResponse = response;
        httpResponse.setContentType("application/json;charset=utf-8");
        String json = new Gson().toJson(R.error(401, "无效的token!"));
        httpResponse.sendError(401,"无效的token!");
        httpResponse.getWriter().print(json);
        return false;
    }

    @Override
    protected boolean onLoginFailure(AuthenticationToken token, AuthenticationException e, ServletRequest request, ServletResponse response) {
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        httpResponse.setContentType("application/json;charset=utf-8");
        try {
            //处理登录失败的异常
            Throwable throwable = e.getCause() == null ? e : e.getCause();
            R r = R.error(401, "授权失败:"+throwable.getMessage());
            String json = new Gson().toJson(r);
            httpResponse.sendError(401,"授权失败:"+throwable.getMessage());
            httpResponse.getWriter().print(json);
        } catch (IOException e1) {

        }

        return false;
    }

    /**
     * 获取请求的token
     */
    private String getRequestToken(HttpServletRequest httpRequest){
        //从header中获取token
        String token = httpRequest.getHeader("token");

        //如果header中不存在token，则从参数中获取token
        if(StringUtils.isBlank(token)){
            token = httpRequest.getParameter("token");
        }
        return token;
    }


}
