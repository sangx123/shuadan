package com.sangxiang.app.controller.login;

import com.sangxiang.app.AppBaseController;
import com.sangxiang.app.AppExecStatus;
import com.sangxiang.app.sdk.token.UserTokenManager;
import com.sangxiang.app.utils.AppResult;
import com.sangxiang.dao.mapper.SysUserMapper;
import com.sangxiang.dao.model.SysUser;
import com.sangxiang.dao.service.SysUserService;
import com.sangxiang.util.StringUtil;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;

@RestController
public class TestController {
    /**
     * 测试接口
     */
    @GetMapping("/test")
    public String index() {
        return "测试的接口";
    }
}
