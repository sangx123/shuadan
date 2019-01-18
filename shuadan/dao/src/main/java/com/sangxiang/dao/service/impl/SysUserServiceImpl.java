package com.sangxiang.dao.service.impl;

import com.sangxiang.base.service.impl.BaseServiceImpl;
import com.sangxiang.dao.mapper.SysUserMapper;
import com.sangxiang.dao.model.SysUser;
import com.sangxiang.dao.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SysUserServiceImpl extends BaseServiceImpl<SysUser> implements SysUserService {
    @Autowired
    SysUserMapper sysUserMapper;
    @Override
    public SysUser queryUser(int userId) {
        return sysUserMapper.selectByPrimaryKey(userId);
    }

    @Override
    public SysUser queryUserRole(int userId) {
        return sysUserMapper.queryUserRole(userId);
    }

    @Override
    public void addUser(SysUser sysUser){
        sysUserMapper.insertUseGeneratedKeys(sysUser);
    }

    @Override
    public SysUser queryUserByUserName(String userName) {
        return sysUserMapper.queryUserByUserName(userName);
    }
}
