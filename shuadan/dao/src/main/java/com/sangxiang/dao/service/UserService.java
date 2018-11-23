package com.sangxiang.dao.service;

import com.sangxiang.dao.model.User;

public interface UserService {
    int insert(User record);
    User getUser();
}
