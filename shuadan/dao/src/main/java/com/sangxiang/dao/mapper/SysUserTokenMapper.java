package com.sangxiang.dao.mapper;

import com.sangxiang.dao.model.SysUserToken;

import java.util.List;

public interface SysUserTokenMapper {
    SysUserToken queryByToken(String token);
}
