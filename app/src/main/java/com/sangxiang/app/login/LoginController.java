package com.sangxiang.app.login;


import com.sangxiang.dao.model.User;
import com.sangxiang.dao.service.UserService;
import com.sangxiang.model.TestClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    @Autowired
    private UserService userService;

    @RequestMapping("/login")
    public Object login(){
//        TestClass test=new TestClass();
//        test.name="sangxiang";
//        test.password="123456";
//        User user=new User();
//        user.setId(2);
//        user.setName("s1");
//        user.setAge(11);
//        user.setPhone("111");


        return userService.getUser();
    }
}
