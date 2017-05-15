package com.redis.shiro;

import com.redis.shiro.config.RedisConfig;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.redis.shiro.mapper")
public class SimpleApplication {
    @Autowired
    private RedisConfig redisConfig;

    public static void main(String[] args) {
        SpringApplication.run(SimpleApplication.class, args);
    }
}
