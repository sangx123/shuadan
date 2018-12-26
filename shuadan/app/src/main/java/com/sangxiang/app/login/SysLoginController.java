package com.sangxiang.app.login;
import com.sangxiang.app.oauth2.ShiroUtils;
import com.sangxiang.base.rest.ApiExecStatus;
import com.sangxiang.base.rest.ApiResult;
import com.sangxiang.base.rest.BaseResource;
import com.sangxiang.dao.model.SysUser;
import com.sangxiang.dao.model.SysUserToken;
import com.sangxiang.dao.service.SysUserService;
import com.sangxiang.dao.service.SysUserTokenService;
import com.sangxiang.model.SysUserLogin;
import com.sangxiang.util.MD5Util;
import com.sangxiang.util.StringUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;

/**
 * 登录相关
 */
@RestController
@Api(description="登录/退出" )
public class SysLoginController extends BaseResource {

	@Autowired
	private SysUserTokenService sysUserTokenService;

	@Autowired
	private SysUserService sysUserService;
//
//	@Autowired
//	private SysOrgService sysOrgService;

	/**
	 * 登录
	 */
	@PostMapping(value = "/login")
	@ApiOperation(value="用户登录")
	public ApiResult<SysUserToken> login(@RequestBody SysUserLogin sysUserLogin){

		SysUser user=null;
		//用户名
		SysUser sysUser1=new SysUser();
		sysUser1.setUsername(sysUserLogin.getUsername());
		user = sysUserService.findOne(sysUser1);

//		//手机号
//		if(user==null){
//			SysUser sysUser2=new SysUser();
//			sysUser2.setMobile(sysUserLogin.getUsername());
//			user = sysUserService.findOne(sysUser2);
//		}
//		//邮箱
//		if(user==null){
//			SysUser sysUser3=new SysUser();
//			sysUser3.setEmail(sysUserLogin.getUsername());
//			user = sysUserService.findOne(sysUser3);
//		}

		//账号不存在、密码错误
		if(user==null || !user.getPassword().equalsIgnoreCase(new Sha256Hash(MD5Util.getMd5Hash(sysUserLogin.getPassword()),user.getSalt()).toHex())) {
			return fail(ApiExecStatus.FAIL,"账号或密码不正确");
		}

		//账号状态
		if(user.getState()!=0){
			return fail(ApiExecStatus.FAIL,"账号未启用,请联系管理员");
		}

		//生成token，并保存到数据库
		SysUserToken sysUserToken = sysUserTokenService.getToken(user.getId());
		return success(sysUserToken);
	}


	/**
	 * 创建用户
	 */
	@ApiOperation(value="注册用户")
	@PostMapping("/register")
	// @RequiresPermissions("sys:user:save")
	public ApiResult register(@RequestBody SysUser sysUser){
		int result=checkUser(sysUser);
		if(0!=result){
			if(result==-1) {
				return  fail(ApiExecStatus.INVALID_PARAM,"username已存在!");
			}
//			else if(result==-2) {
//				return  fail(ApiExecStatus.INVALID_PARAM,"手机号已存在!");
//			} else if(result==-3) {
//				return  fail(ApiExecStatus.INVALID_PARAM,"email已存在!");
//			}
		}

		sysUser.setCreateTime(new Date());
		//sha256加密
		String salt = RandomStringUtils.randomAlphanumeric(20);
		sysUser.setPassword(new Sha256Hash(MD5Util.getMd5Hash(sysUser.getPassword()),salt).toHex());
		sysUser.setSalt(salt);
		//用户状态：0-启用；1-停用；2-锁定；
		sysUser.setState(0);
		sysUser.setUsername(sysUser.getUsername().trim());
		sysUserService.addUser(sysUser);
		return success("success");
	}

	private int checkUser(SysUser sysUser){
		int result=0;
		Example example=new Example(SysUser.class);
		if(StringUtil.isNotEmpty(sysUser.getUsername())){
			example.clear();
			Example.Criteria criteria=example.createCriteria();
			if(null!=sysUser.getId()) {
				criteria.andNotEqualTo("id",sysUser.getId());
			}
			criteria.andEqualTo("username",sysUser.getUsername());
			List<SysUser> list=sysUserService.selectByExample(example);
			if(null!=list && list.size()>0) {
				result=-1;
			}
		}
//		if(StringUtil.isNotEmpty(sysUser.getMobile())){
//			example.clear();
//			Example.Criteria criteria=example.createCriteria();
//			if(null!=sysUser.getId()) {
//				criteria.andNotEqualTo("id",sysUser.getId());
//			}
//			criteria.andEqualTo("mobile",sysUser.getMobile());
//			List<SysUser> list=sysUserService.selectByExample(example);
//			if(null!=list && list.size()>0) {
//				result=-2;
//			}
//		}
//		if(StringUtil.isNotEmpty(sysUser.getEmail())){
//			example.clear();
//			Example.Criteria criteria=example.createCriteria();
//			if(null!=sysUser.getId()) {
//				criteria.andNotEqualTo("id",sysUser.getId());
//			}
//			criteria.andEqualTo("email",sysUser.getEmail());
//			List<SysUser> list=sysUserService.selectByExample(example);
//			if(null!=list && list.size()>0) {
//				result=-3;
//			}
//		}
		return  result;
	}

	/**
	 * 退出
	 */
	@PostMapping(value = "/sys/logout")
	@ApiOperation(value="用户退出")
	public ApiResult logout(){
		int userId=ShiroUtils.getUserId();
		sysUserTokenService.expireToken(userId);
		ShiroUtils.logout();
		return success("success");
	}

}
