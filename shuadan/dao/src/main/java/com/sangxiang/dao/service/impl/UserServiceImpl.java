package com.sangxiang.dao.service.impl;

import com.sangxiang.dao.mapper.UserMapper;
import com.sangxiang.dao.model.User;
import com.sangxiang.dao.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserMapper userMapper;
    @Override
    public int insert(User record) {
        return userMapper.insert(record);
    }

    @Override
    public User getUser() {
        return userMapper.getUser();
    }
}
