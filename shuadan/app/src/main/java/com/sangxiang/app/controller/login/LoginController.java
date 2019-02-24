package com.sangxiang.app.controller.login;

import com.sangxiang.app.AppBaseController;
import com.sangxiang.app.AppExecStatus;
import com.sangxiang.app.sdk.token.UserTokenManager;
import com.sangxiang.app.utils.AppResult;
import com.sangxiang.base.rest.ApiExecStatus;
import com.sangxiang.base.rest.ApiResult;
import com.sangxiang.dao.model.SysUser;
import com.sangxiang.dao.service.SysUserService;
import com.sangxiang.util.MD5Util;
import com.sangxiang.util.StringUtil;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/index")
public class LoginController extends AppBaseController {
    @Autowired
    private SysUserService sysUserService;

    @PostMapping("/login")
    public AppResult<UserLoginInfo> login(@RequestBody UserLoginParam data) {
        String pushToken = null!=data.getPushToken()?data.getPushToken():null;
        String mobile = data.getMobile();
        String password = data.getPassword();

        checkParam(mobile, "缺少账号");
        checkParam(password, "缺少密码");

        SysUser loginInfo = sysUserService.authenticate(mobile, password, pushToken);
        if(loginInfo == null) {
            return AppResult.busErrorRes("用户或密码错误！");
        }

        String userToken = UserTokenManager.getInstance().saveUserToken(loginInfo.getId().longValue());
        UserLoginInfo userLoginInfo=new UserLoginInfo();
        userLoginInfo.setUserToken(userToken);
        return success(userLoginInfo);
    }

    /**
     * 创建用户
     */
    @ApiOperation(value="注册用户")
    @PostMapping("/register")
    // @RequiresPermissions("sys:user:save")
    public AppResult register(@RequestBody SysUser sysUser){
        Example example=new Example(SysUser.class);
        if(StringUtil.isNotEmpty(sysUser.getMobile())){
			example.clear();
			Example.Criteria criteria=example.createCriteria();
			if(null!=sysUser.getId()) {
				criteria.andNotEqualTo("id",sysUser.getId());
			}
			criteria.andEqualTo("mobile",sysUser.getMobile());
			List<SysUser> list=sysUserService.selectByExample(example);
			if(null!=list && list.size()>0) {
                return fail(AppExecStatus.FAIL, "该手机号已注册!");
			}
		}
        sysUser.setCreateTime(new Date());
        //sha256加密
        String salt = RandomStringUtils.randomAlphanumeric(20);
        sysUser.setPassword(new Sha256Hash(sysUser.getPassword(),salt).toHex());
        sysUser.setSalt(salt);
        //用户状态：0-启用；1-停用；2-锁定；
        sysUser.setState(0);
        sysUser.setMobile(sysUser.getMobile());
        sysUserService.addUser(sysUser);
        return success("success");
    }

}
