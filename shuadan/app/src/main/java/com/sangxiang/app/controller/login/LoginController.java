package com.sangxiang.app.controller.login;

import com.sangxiang.app.AppBaseController;
import com.sangxiang.app.AppExecStatus;
import com.sangxiang.app.sdk.token.UserTokenManager;
import com.sangxiang.app.utils.AppResult;
import com.sangxiang.base.rest.ApiExecStatus;
import com.sangxiang.base.rest.ApiResult;
import com.sangxiang.dao.mapper.SysUserMapper;
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

    @Autowired
    private SysUserMapper sysUserMapper;
    /**
     * 用户登录
     */
    @PostMapping("/login")
    public AppResult<UserLoginInfo> login(@RequestBody UserLoginParam data) {
        String pushToken = null!=data.getPushToken()?data.getPushToken():null;
        String mobile = data.getMobile();
        String password = data.getPassword();

        checkParam(mobile, "缺少账号");
        checkParam(password, "缺少密码");

        SysUser loginInfo = sysUserService.authenticateName(mobile, password, pushToken);
        if(loginInfo == null) {
            loginInfo = sysUserService.authenticateMobile(mobile, password, pushToken);
        }
        if(loginInfo==null){
            return  fail(AppExecStatus.FAIL,"用户名或手机号不存在!");
        }

        String userToken = UserTokenManager.getInstance().saveUserToken(loginInfo.getId().longValue());
        UserLoginInfo userLoginInfo=new UserLoginInfo();
        userLoginInfo.setUserToken(userToken);
        return success(userLoginInfo);
    }

    /**
     * 用户重置密码
     * @param data
     * @return
     */
    @PostMapping("/resetPassword")
    public AppResult resetPassword(@RequestBody UserLoginParam data) {
        SysUser user=null;
        String pushToken = null!=data.getPushToken()?data.getPushToken():null;
        String mobile = data.getMobile();
        String password = data.getPassword();
        checkParam(mobile, "缺少账号");
        checkParam(password, "缺少密码");

        user= sysUserMapper.fetchOneByMobile(data.getMobile());
        if(user==null){
            return  fail(AppExecStatus.FAIL,"该手机号不存在!");
        }
        String salt = RandomStringUtils.randomAlphanumeric(20);
        user.setPassword(new Sha256Hash(password,salt).toHex());
        user.setSalt(salt);
        sysUserMapper.updateByPrimaryKey(user);
        return success("success");
    }


    /**
     * 创建用户
     */
    @ApiOperation(value="注册用户")
    @PostMapping("/register")
    // @RequiresPermissions("sys:user:save")
    public AppResult<UserLoginInfo> register(@RequestBody SysUser sysUser){
        int result=checkUser(sysUser);
        if(0!=result){
            if(result==-1) {
                return  fail(AppExecStatus.FAIL,"用户名已存在!");
            }
			else if(result==-2) {
				return  fail(AppExecStatus.FAIL,"手机号已存在!");
			}
        }
        sysUser.setCreateTime(new Date());
        //sha256加密
        String salt = RandomStringUtils.randomAlphanumeric(20);
        sysUser.setPassword(new Sha256Hash(sysUser.getPassword(),salt).toHex());
        sysUser.setSalt(salt);
        //用户状态：0-启用；1-停用；2-锁定；
        sysUser.setState(0);
        sysUser.setName(sysUser.getName());
        sysUser.setMobile(sysUser.getMobile());
        sysUser.setMoney(0f);
        sysUserService.addUser(sysUser);
        String userToken = UserTokenManager.getInstance().saveUserToken(sysUser.getId().longValue());
        UserLoginInfo userLoginInfo=new UserLoginInfo();
        userLoginInfo.setUserToken(userToken);
        return success(userLoginInfo);
    }

    private int checkUser(SysUser sysUser){
        int result=0;
        Example example=new Example(SysUser.class);
        if(StringUtil.isNotEmpty(sysUser.getName())){
            example.clear();
            Example.Criteria criteria=example.createCriteria();
            if(null!=sysUser.getId()) {
                criteria.andNotEqualTo("id",sysUser.getId());
            }
            criteria.andEqualTo("name",sysUser.getName());
            List<SysUser> list=sysUserService.selectByExample(example);
            if(null!=list && list.size()>0) {
                result=-1;
                return  result;
            }
        }
		if(StringUtil.isNotEmpty(sysUser.getMobile())){
			example.clear();
			Example.Criteria criteria=example.createCriteria();
			if(null!=sysUser.getId()) {
				criteria.andNotEqualTo("id",sysUser.getId());
			}
			criteria.andEqualTo("mobile",sysUser.getMobile());
			List<SysUser> list=sysUserService.selectByExample(example);
			if(null!=list && list.size()>0) {
				result=-2;
                return  result;
			}
		}
        return  result;
    }

}
