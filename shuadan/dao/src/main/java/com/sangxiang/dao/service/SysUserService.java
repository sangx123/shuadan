package com.sangxiang.dao.service;

import com.sangxiang.base.service.BaseService;
import com.sangxiang.dao.model.SysUser;

import java.util.List;

public interface SysUserService extends BaseService<SysUser> {
    SysUser queryUser(int userId);

    SysUser queryUserRole(int userId);
    /**
     * 创建用户
     * @param sysUser
     */
    void addUser(SysUser sysUser);


    SysUser authenticate(String mobile, String password, String pushToken);
}