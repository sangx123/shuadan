package com.sangxiang.dao.mapper;

import com.sangxiang.base.mapper.MyMapper;
import com.sangxiang.dao.model.SysUser;

import java.util.List;

public interface SysUserMapper extends MyMapper<SysUser> {
    SysUser queryUserRole(int userId);
    SysUser fetchOneByMobile(String mobile);
}