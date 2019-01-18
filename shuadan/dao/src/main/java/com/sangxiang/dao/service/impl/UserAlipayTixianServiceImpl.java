package com.sangxiang.dao.service.impl;

import com.sangxiang.base.service.impl.BaseServiceImpl;
import com.sangxiang.dao.mapper.SysUserMapper;
import com.sangxiang.dao.mapper.UserAlipayTixianMapper;
import com.sangxiang.dao.mapper.UserMoneyHistoryMapper;
import com.sangxiang.dao.model.SysUser;
import com.sangxiang.dao.model.UserAlipayTixian;
import com.sangxiang.dao.model.UserMoneyHistory;
import com.sangxiang.dao.service.UserAlipayTixianService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class UserAlipayTixianServiceImpl extends BaseServiceImpl<UserAlipayTixian> implements UserAlipayTixianService {
    @Autowired
    UserAlipayTixianMapper userAlipayTixianMapper;
    @Autowired
    SysUserMapper sysUserMapper;

    @Autowired
    UserMoneyHistoryMapper userMoneyHistoryMapper;
    @Transactional
    @Override
    public void applyTixian(UserAlipayTixian userAlipayTixian, SysUser user) {
        UserMoneyHistory userMoneyHistory=new UserMoneyHistory();
        userMoneyHistory.setCreateTime(new Date());
        userMoneyHistory.setMoneyBefore(user.getMoney());
        userMoneyHistory.setUserId(user.getId());
          userAlipayTixian.setUserId(user.getId());
          userAlipayTixian.setUserTixianStatus(0);
          userAlipayTixian.setUserTixianStatus(0);
          userAlipayTixian.setUserTixianTime(new Date());
          user.setMoney(user.getMoney()-userAlipayTixian.getUserTixianMoney());
          userAlipayTixian.setUserTixianLeftMoney(user.getMoney());
          userAlipayTixianMapper.insertUseGeneratedKeys(userAlipayTixian);
          sysUserMapper.updateByPrimaryKey(user);
          //插入收支历史记录
         userMoneyHistory.setMoneyAfter(user.getMoney());
         userMoneyHistory.setMoneyAdd(userMoneyHistory.getMoneyAfter()-userMoneyHistory.getMoneyBefore());
         userMoneyHistory.setType(UserMoneyHistory.tixian);
         userMoneyHistory.setDescription("提取现金");
         userMoneyHistoryMapper.insertUseGeneratedKeys(userMoneyHistory);


    }

    @Override
    public List<UserAlipayTixian> getTixianFromDay(int userId, Date startDate, Date endDate) {
        return userAlipayTixianMapper.getTixianFromDay(userId,startDate,endDate);
    }

    @Override
    public List<UserAlipayTixian> getTixianByUser(int userId, int status) {
        return userAlipayTixianMapper.getTixianByUser(userId,status);
    }

}
