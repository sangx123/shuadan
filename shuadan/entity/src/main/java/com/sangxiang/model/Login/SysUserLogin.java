package com.sangxiang.model.Login;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value="用户登录参数")
public class SysUserLogin{
    @ApiModelProperty(value="账号",name="username")
    private String username;
    @ApiModelProperty(value="密码",name="password")
    private String password;
}
