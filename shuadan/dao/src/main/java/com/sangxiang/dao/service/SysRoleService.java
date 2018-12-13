package com.sangxiang.dao.service;

import com.sangxiang.base.mapper.MyMapper;
import com.sangxiang.dao.model.SysRole;

import java.util.List;

public interface SysRoleService  {
   SysRole queryRolePermission(int roleId);
}