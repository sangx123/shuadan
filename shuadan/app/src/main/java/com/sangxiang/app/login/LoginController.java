package com.sangxiang.app.login;


import com.sangxiang.dao.model.User;
import com.sangxiang.dao.service.UserService;
import com.sangxiang.model.TestClass;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

//@RestController
//@Slf4j
//public class LoginController {
//
//    @Autowired
//    private UserService userService;
//
//    @RequestMapping("/login")
//    public Object login(){
//        log.error("sangxianghaha ");
//        return userService.getUser();
//    }
//}
@Controller(value = "LoginController")
public class LoginController {

    //登录页(shiro配置需要两个/login 接口,一个是get用来获取登陆页面,一个用post用于登录,这是一个坑)
    @RequestMapping(value="/login", method = RequestMethod.GET)
    public String login() {
        return "login";
    }

    // 登录提交地址和applicationontext-shiro.xml配置的loginurl一致。 (配置文件方式的说法)
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(HttpServletRequest request, Map<String, Object> map)
            throws Exception {

        // 登录失败从request中获取shiro处理的异常信息。shiroLoginFailure:就是shiro异常类的全类名.
        Object exception = request.getAttribute("shiroLoginFailure");
        String msg = "";
        if (exception != null){
            if (UnknownAccountException.class.isInstance(exception)) {
                msg = "提示->账号不存在";
            } else if (IncorrectCredentialsException.class.isInstance(exception)) {
                msg = "提示->密码不正确";
            } else {
                msg = "提示->未知错误";
            }
            map.put("msg", msg);
            return "login";
        }
        //如果已经登录，直接跳转主页面
        return "index";
    }

    //主页
    @RequestMapping({"/","/index"})
    public String index(HttpServletRequest request,Model model){
        return "index";
    }

    //hello页面
    //需要权限
    @RequestMapping(value = "/hello")
    @RequiresPermissions(value = {"permission:view"})
    public String hello(HttpServletRequest request,Model model){
        return "hello";
    }

    //aix页面
    //需要权限
    @RequestMapping(value = "/aix")
    @RequiresPermissions(value = {"permission:aix"})
    public String aix(HttpServletRequest request,Model model){
        return "aix";
    }
}