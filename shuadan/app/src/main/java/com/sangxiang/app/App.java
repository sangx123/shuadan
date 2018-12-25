package com.sangxiang.app;
import com.sangxiang.base.mapper.MyMapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement // 开启注解事务管理，等同于xml配置文件中的 <tx:annotation-driven />
@MapperScan(basePackages ="com.sangxiang.dao.mapper" , markerInterface = MyMapper.class)
//mybaties时候出错
@ComponentScan(basePackages = {"com.sangxiang.dao","com.sangxiang.app","com.sangxiang.util"})
public class App {
    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }
}
