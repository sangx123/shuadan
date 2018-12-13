package com.sangxiang.dao.service;

import com.sangxiang.dao.model.SysUser;

import java.util.List;

public interface SysUserService {
    SysUser queryUser(Long userId);
    SysUser queryUserRole(int userId);
}