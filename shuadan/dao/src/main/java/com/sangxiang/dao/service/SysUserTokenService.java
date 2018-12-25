package com.sangxiang.dao.service;

import com.sangxiang.base.service.BaseService;
import com.sangxiang.dao.model.SysUserToken;

public interface SysUserTokenService extends BaseService<SysUserToken> {
    SysUserToken queryByToken(String token);

    /**
     * 生成token
     * @param id  用户id
     */
    SysUserToken getToken(int id);


    /**
     * 设置token过期
     * @param id  用户id
     */
    void expireToken(int id);

}