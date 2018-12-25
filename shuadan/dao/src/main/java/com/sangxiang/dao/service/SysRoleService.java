package com.sangxiang.dao.service;

import com.sangxiang.base.mapper.MyMapper;
import com.sangxiang.base.service.BaseService;
import com.sangxiang.dao.model.SysRole;

import java.util.List;

public interface SysRoleService extends BaseService<SysRole> {
   SysRole queryRolePermission(int roleId);
}