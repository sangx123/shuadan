package com.sangxiang.dao.mapper;

import com.sangxiang.dao.User;

public interface UserMapper {
    int insert(User record);

    int insertSelective(User record);
}