package com.sangxiang.app.money;
import com.github.pagehelper.PageInfo;
import com.sangxiang.app.oauth2.ShiroUtils;
import com.sangxiang.base.rest.ApiExecStatus;
import com.sangxiang.base.rest.ApiResult;
import com.sangxiang.base.rest.BaseResource;
import com.sangxiang.dao.model.SysUserToken;
import com.sangxiang.dao.model.UserAlipay;
import com.sangxiang.dao.model.UserAlipayTixian;
import com.sangxiang.dao.model.UserMoneyHistory;
import com.sangxiang.dao.service.UserAlipayService;
import com.sangxiang.dao.service.UserAlipayTixianService;
import com.sangxiang.dao.service.UserMoneyHistoryService;
import com.sangxiang.model.BasePagerParam;
import com.sangxiang.model.Login.SysUserLogin;
import com.sangxiang.model.Money.AlipayParam;
import com.sangxiang.util.DateUtils;
import com.sangxiang.util.MD5Util;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * 登录相关
 */
@RestController
@Api(description="支付宝支付" )
public class MoneyController extends BaseResource {

//
	@Autowired
	private UserAlipayService userAlipayService;

	@Autowired
	private UserAlipayTixianService userAlipayTixianService;

	@Autowired
	private UserMoneyHistoryService userMoneyHistoryService;
	/**
	 *支付宝支付
	 */
	@PostMapping(value = "/alipay")
	@ApiOperation(value="支付宝支付")
	public ApiResult<String> alipay(@RequestBody MultiValueMap<String, String> req) {
		String key = "sangxiang";
		UserAlipay param=new UserAlipay();
		param.setTradeno(req.get("tradeNo").get(0));
		param.setDescription(req.get("desc").get(0));
		param.setCreateTime(req.get("time").get(0));
		param.setUsername(req.get("username").get(0));
		param.setUserid(req.get("userid").get(0));
		param.setAmount(req.get("amount").get(0));
		param.setStatus(req.get("status").get(0));
		param.setSig(req.get("sig").get(0));
		List<String> list=new ArrayList<>();
		list.add(param.getTradeno());
		list.add(param.getDescription());
		list.add(param.getCreateTime());
		list.add(param.getUsername());
		list.add(param.getUserid());
		list.add(param.getAmount());
		list.add(param.getStatus());
		list.add(key);
		String str=StringUtils.join(list,"|");
		if(StringUtils.isNotEmpty(param.getSig())&&!StringUtils.isWhitespace(param.getSig())&&MD5Util.getMd5Hash(str).equalsIgnoreCase(param.getSig())) {
			//去重
			userAlipayService.insert(param);
			return  success("添加成功");
		}else {
			 return fail(ApiExecStatus.FAIL,"sig不匹配！");
		}

	}


	/**
	 *支付宝支付验证交易号
	 */
	@PostMapping(value = "/checkAlipay")
	@ApiOperation(value="支付宝支付验证交易号")
	public ApiResult<String> checkAlipay(@RequestBody UserAlipay userAlipay) {
		return 	success(userAlipayService.updateAlipay(userAlipay, ShiroUtils.getUser()));
	}

	/**
	 *申请提现
	 */
	@PostMapping(value = "/applyTixian")
	@ApiOperation(value="申请提现")

	public ApiResult<String> applyTixian(@RequestBody UserAlipayTixian userAlipayTixian) {
		if(ShiroUtils.getUser().getMoney()<userAlipayTixian.getUserTixianMoney()){
			return fail(ApiExecStatus.FAIL,"您的余额不足");
		}
		if(userAlipayTixian.getUserTixianMoney()<50){
			 return fail(ApiExecStatus.FAIL,"提现的最低额度是50元");
		}
		if(userAlipayTixianService.getTixianFromDay(ShiroUtils.getUserId(), DateUtils.getTodayStartTime(),DateUtils.getTodayEndTime()).isEmpty()){
			userAlipayTixianService.applyTixian(userAlipayTixian,ShiroUtils.getUser());
			return 	success("已申请提现！");
		}else {
			return fail(ApiExecStatus.FAIL,"一天只可以提现一次！");
		}
	}

	@PostMapping(value = "/moneyHistory")
	@ApiOperation(value="任务收支明细")
	public ApiResult<PageInfo<UserMoneyHistory>> moneyHistory(@RequestBody BasePagerParam param){
		PageInfo<UserMoneyHistory> pageInfo= userMoneyHistoryService.findPage(param.getPageNumber(),param.getPageSize(),ShiroUtils.getUserId());
		return success(pageInfo);
	}

}
