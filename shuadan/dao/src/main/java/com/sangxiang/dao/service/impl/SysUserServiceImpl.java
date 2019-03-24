package com.sangxiang.dao.service.impl;

import com.sangxiang.base.service.impl.BaseServiceImpl;
import com.sangxiang.dao.mapper.SysUserMapper;
import com.sangxiang.dao.model.SysUser;
import com.sangxiang.dao.service.SysUserService;
import com.sangxiang.util.RegexMatcher;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.crypto.hash.Sha256Hash;
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
    public SysUser authenticateMobile(String mobile, String password, String pushToken) {
        SysUser user = null;
        if (RegexMatcher.isPhoneNumber(mobile)) {
            user = sysUserMapper.fetchOneByMobile(mobile);
        }

        if (user == null) {
            return null;
        }

        if (0 != user.getState()) {
            return null;
        }

        if (!StringUtils.equalsIgnoreCase(user.getPassword(), new Sha256Hash(password, user.getSalt()).toHex())) {
            return null;
        }

        return user;
    }

    @Override
    public SysUser authenticateName(String name, String password, String pushToken) {
        SysUser user = sysUserMapper.fetchOneByName(name);

        if (user == null) {
            return null;
        }

        if (0 != user.getState()) {
            return null;
        }

        if (!StringUtils.equalsIgnoreCase(user.getPassword(), new Sha256Hash(password, user.getSalt()).toHex())) {
            return null;
        }

        return user;
    }
}
