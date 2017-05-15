package com.redis.shiro.config;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.stereotype.Component;

@EnableRedisHttpSession
@Component
public class RedisConfig {
    @Value("${spring.redis.database}")
    static Integer database;
    @Value("${spring.redis.port}")
    static Integer port;
    @Value("${spring.redis.host}")
    static String host;
    @Value("${spring.redis.password}")
    static String password;


}
