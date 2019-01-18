package com.sangxiang.dao.service;

import com.sangxiang.base.service.BaseService;
import com.sangxiang.dao.model.SysUser;
import com.sangxiang.dao.model.UserAlipayTixian;

import java.util.Date;
import java.util.List;

public interface UserAlipayTixianService extends BaseService<UserAlipayTixian> {
    void applyTixian(UserAlipayTixian userAlipayTixian, SysUser user);
    List<UserAlipayTixian> getTixianFromDay(int userId,Date startDate,Date endDate);
    List<UserAlipayTixian> getTixianByUser(int userId,int status);
}