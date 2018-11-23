package com.sangxiang.app;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@MapperScan("com.sangxiang.dao.mapper")
//mybaties时候出错
@ComponentScan(basePackages = {"com.sangxiang.dao","com.sangxiang.app"})
public class App {
    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }
}
