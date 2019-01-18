package com.sangxiang.dao.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sangxiang.base.service.impl.BaseServiceImpl;
import com.sangxiang.dao.mapper.*;
import com.sangxiang.dao.model.*;
import com.sangxiang.dao.service.UserAlipayService;
import com.sangxiang.dao.service.UserTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;


@Service
public class UserAlipayServiceImpl extends BaseServiceImpl<UserAlipay>  implements UserAlipayService {

    @Autowired
    UserAlipayMapper userAlipayMapper;


    @Autowired
    SysUserMapper sysUserMapper;

    @Autowired

    UserMoneyHistoryMapper userMoneyHistoryMapper;
    //添加充值记录
    @Override
    public void insert(UserAlipay userAlipay) {
      UserAlipay model= userAlipayMapper.selectByPrimaryKey(userAlipay.getTradeno());
      if(model==null){
          userAlipayMapper.insert(userAlipay);
      }
    }
    @Transactional
    //更新充值记录
    @Override
    public String updateAlipay(UserAlipay userAlipay, SysUser sysUser) {
        UserAlipay model= userAlipayMapper.selectByPrimaryKey(userAlipay.getTradeno());
        if(model==null){
           return "";
        }else if(model.getUseStatus()){
            return "已充值";
        }else {
            UserMoneyHistory userMoneyHistory=new UserMoneyHistory();
            userMoneyHistory.setCreateTime(new Date());
            userMoneyHistory.setMoneyBefore(sysUser.getMoney());
            userMoneyHistory.setUserId(sysUser.getId());
            model.setUseStatus(true);
            model.setUseUserid(sysUser.getId());
            model.setUseTime(new Date());
            sysUser.setMoney(sysUser.getMoney()==null?0:sysUser.getMoney()+Float.valueOf(model.getAmount()));
            sysUserMapper.updateByPrimaryKey(sysUser);
            userAlipayMapper.updateByPrimaryKey(model);
            //插入收支历史记录
            userMoneyHistory.setMoneyAfter(sysUser.getMoney());
            userMoneyHistory.setMoneyAdd(userMoneyHistory.getMoneyAfter()-userMoneyHistory.getMoneyBefore());
            userMoneyHistory.setType(UserMoneyHistory.chongzhi);
            userMoneyHistory.setDescription("现金充值");
            userMoneyHistoryMapper.insertUseGeneratedKeys(userMoneyHistory);
            return "充值成功";
        }
    }
}
