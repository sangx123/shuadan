package com.sangxiang.dao.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sangxiang.base.service.impl.BaseServiceImpl;
import com.sangxiang.dao.mapper.TaskMapper;
import com.sangxiang.dao.mapper.UserAlipayMapper;
import com.sangxiang.dao.mapper.UserTaskMapper;
import com.sangxiang.dao.model.SysUser;
import com.sangxiang.dao.model.Task;
import com.sangxiang.dao.model.UserAlipay;
import com.sangxiang.dao.model.UserTask;
import com.sangxiang.dao.service.UserAlipayService;
import com.sangxiang.dao.service.UserTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class UserAlipayServiceImpl extends BaseServiceImpl<UserAlipay>  implements UserAlipayService {

    @Autowired
    UserAlipayMapper userAlipayMapper;

    @Override
    public void insert(UserAlipay userAlipay) {
      UserAlipay model= userAlipayMapper.selectByPrimaryKey(userAlipay.getTradeno());
      if(model==null){
          userAlipayMapper.insert(userAlipay);
      }
    }
}
