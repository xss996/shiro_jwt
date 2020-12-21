package com.joeshaw.shiro_jwt;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.joeshaw.shiro_jwt.mapper")
public class ShiroJwtApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShiroJwtApplication.class, args);
    }

}
