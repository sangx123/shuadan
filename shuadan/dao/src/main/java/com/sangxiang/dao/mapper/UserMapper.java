package com.sangxiang.dao.mapper;


import com.sangxiang.dao.model.User;

public interface UserMapper {
    int insert(User record);
    User getUser();
}