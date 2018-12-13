package com.sangxiang.app.oauth2;


import org.apache.shiro.authc.AuthenticationToken;

/**
 * token
 */
public class OAuth2Token implements AuthenticationToken {
    
	private static final long serialVersionUID = 1L;
	private String token;

    public OAuth2Token(String token){
        this.token = token;
    }

    @Override
    public String getPrincipal() {
        return token;
    }

    @Override
    public Object getCredentials() {
        return token;
    }
}
