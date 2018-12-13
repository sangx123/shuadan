package com.sangxiang.dao.service.impl;

import com.sangxiang.dao.mapper.SysUserTokenMapper;
import com.sangxiang.dao.model.SysUserToken;
import com.sangxiang.dao.service.SysUserTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SysUserTokenServiceImpl implements SysUserTokenService {
    @Autowired
    SysUserTokenMapper sysUserTokenMapper;
    @Override
    public SysUserToken queryByToken(String token) {
        return sysUserTokenMapper.queryByToken(token);
    }

}
