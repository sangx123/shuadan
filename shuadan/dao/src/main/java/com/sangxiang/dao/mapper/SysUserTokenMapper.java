package com.sangxiang.dao.mapper;

import com.sangxiang.base.mapper.MyMapper;
import com.sangxiang.dao.model.SysUserToken;

import java.util.List;

public interface SysUserTokenMapper extends MyMapper<SysUserToken> {
    SysUserToken queryByToken(String token);
}
