package com.sangxiang.dao.service;

import com.sangxiang.dao.model.SysUserToken;

public interface SysUserTokenService {
    SysUserToken queryByToken(String token);

}