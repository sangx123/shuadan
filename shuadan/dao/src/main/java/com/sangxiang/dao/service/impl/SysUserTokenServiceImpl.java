package com.sangxiang.dao.service.impl;

import com.sangxiang.base.service.impl.BaseServiceImpl;
import com.sangxiang.dao.mapper.SysUserTokenMapper;
import com.sangxiang.dao.model.SysUser;
import com.sangxiang.dao.model.SysUserToken;
import com.sangxiang.dao.service.SysUserTokenService;
import com.sangxiang.security.token.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
public class SysUserTokenServiceImpl extends BaseServiceImpl<SysUserToken> implements SysUserTokenService {
    @Autowired
    SysUserTokenMapper sysUserTokenMapper;
    @Override
    public SysUserToken queryByToken(String token) {
        return sysUserTokenMapper.queryByToken(token);
    }

    // token 过期时间， 默认2小时后过期
    @Value("${token.expire}")
    private int expire;

    @Override
    public SysUserToken getToken(int userId) {
        //当前时间
        Date now = new Date();
        //过期时间
        Date expireTime = new Date(now.getTime() + expire * 1000 * 3600);

        //判断是否生成过token
        SysUserToken tokenEntity = sysUserTokenMapper.selectByPrimaryKey(userId);
        if(tokenEntity == null){
            tokenEntity = new SysUserToken();
            tokenEntity.setUserid(userId);
            //创建token
            tokenEntity.setToken(TokenUtils.generateValue());
            tokenEntity.setUpdateTime(now);
            tokenEntity.setExpireTime(expireTime);
            //保存token
            sysUserTokenMapper.insertSelective(tokenEntity);
        }else{
            tokenEntity.setUpdateTime(now);
            tokenEntity.setExpireTime(expireTime);
            //更新token有效期
            sysUserTokenMapper.updateByPrimaryKeySelective(tokenEntity);
        }
        return tokenEntity;
    }

    @Override
    public void expireToken(int id){
        Date now = new Date();
        SysUserToken tokenEntity = new SysUserToken();
        tokenEntity.setUserid(id);
        tokenEntity.setUpdateTime(now);
        tokenEntity.setExpireTime(now);
        sysUserTokenMapper.updateByPrimaryKeySelective(tokenEntity);
    }



}
