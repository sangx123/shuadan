package com.sangxiang.dao.mapper;

import com.sangxiang.base.mapper.MyMapper;
import com.sangxiang.dao.model.SysPermission;
import com.sangxiang.dao.model.SysRole;

import java.util.List;

public interface SysRoleMapper extends MyMapper<SysRole> {
    SysRole queryRolePermission(int roleId);
}