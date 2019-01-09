package com.sangxiang.dao.service;

import com.sangxiang.base.mapper.MyMapper;
import com.sangxiang.base.service.BaseService;
import com.sangxiang.dao.model.UserAlipay;

public interface UserAlipayService extends BaseService<UserAlipay> {
    void insert(UserAlipay userAlipay);
}