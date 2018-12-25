package com.sangxiang.app.oauth2;
import com.sangxiang.dao.model.SysPermission;
import com.sangxiang.dao.model.SysRole;
import com.sangxiang.dao.model.SysUser;
import com.sangxiang.dao.model.SysUserToken;
import com.sangxiang.dao.service.SysRoleService;
import com.sangxiang.dao.service.SysUserService;
import com.sangxiang.dao.service.SysUserTokenService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
//自定义realm

/**
 * 认证
 */
@Component
@Slf4j
public class OAuth2Realm extends AuthorizingRealm {
    @Autowired
    private SysUserTokenService sysUserTokenService;

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    SysRoleService roleService;

    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof OAuth2Token;
    }

    /**
     * 授权(验证权限时调用)
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        //principals为SimpleAuthenticationInfo中的第一个参数
//        SysUser user = (SysUser)principals.getPrimaryPrincipal();
//        SysUser userRole= sysUserService.queryUserRole(user.getId());
//        //暂时定位一个角色只有一个权限
//        SysRole roles = roleService.queryRolePermission(userRole.getRole().getId());
//        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
//        info.addRole(userRole.getName());
//        for(SysPermission permission:roles.getPermissionList()){
//            info.addStringPermission(permission.getPermission());
//        }
//        return info;
        return null;
    }

    /**
     * 认证(登录时调用)
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        String accessToken = (String) token.getPrincipal();


        //根据accessToken，查询用户信息
        SysUserToken tokenEntity = sysUserTokenService.queryByToken(accessToken);
        //token失效
        if(tokenEntity == null || tokenEntity.getExpireTime().getTime() < System.currentTimeMillis()){
            throw new IncorrectCredentialsException("无效的token!");
        }

        //查询用户信息
        SysUser user = sysUserService.queryUser(tokenEntity.getUserid());

        if(user == null) {
            throw new UnknownAccountException("账号或密码不正确!");
        }

        //账号锁定
        if(user.getState()!=0){
            throw new LockedAccountException("账号未启用,请联系管理员!");
        }
        //参数1：user信息，参数2：密码，参数3：getName()当前realm名字
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(user, accessToken, getName());
//        log.info("::: Remember Tenancy Id :::");
//        TenancyContext.getInstance().setOrgId(user.getOrgId().longValue());
//        log.info(String.format("::: Tenancy Id: [%d] ", TenancyContext.getInstance().getOrgId()));

        return info;
    }
}
