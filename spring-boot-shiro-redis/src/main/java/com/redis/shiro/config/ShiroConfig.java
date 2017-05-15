package com.redis.shiro.config;

import java.util.Map;

import javax.servlet.DispatcherType;
import javax.servlet.Filter;

import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.filter.authc.AnonymousFilter;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.filter.DelegatingFilterProxy;

import com.google.common.collect.Maps;

@Configuration
public class ShiroConfig {

    private String hosts;

    private String port;

    private String password;

    /**
     * FilterRegistrationBean
     *
     * @return
     */
    @Bean
    public FilterRegistrationBean filterRegistrationBean() {
        FilterRegistrationBean filterRegistration = new FilterRegistrationBean();
        filterRegistration.setFilter(new DelegatingFilterProxy("shiroFilter"));
        filterRegistration.setEnabled(true);
        filterRegistration.addUrlPatterns("/*");
        filterRegistration.setDispatcherTypes(DispatcherType.REQUEST);
        return filterRegistration;
    }

    /**
     * @return
     * @see ShiroFilterFactoryBean
     */
    @Bean(name = "shiroFilter")
    public ShiroFilterFactoryBean shiroFilter() {
        ShiroFilterFactoryBean bean = new ShiroFilterFactoryBean();
        bean.setSecurityManager(securityManager());
        bean.setLoginUrl("/login");
        bean.setUnauthorizedUrl("/unauthor");

        Map<String, Filter> filters = Maps.newHashMap();
        filters.put("perms", urlPermissionsFilter());
        filters.put("anon", new AnonymousFilter());
        bean.setFilters(filters);

        Map<String, String> chains = Maps.newHashMap();
        chains.put("/login", "anon");
        chains.put("/unauthor", "anon");
        chains.put("/logout", "logout");
        chains.put("/base/**", "anon");
        chains.put("/css/**", "anon");
        chains.put("/layer/**", "anon");
        chains.put("/**", "authc,perms");
        bean.setFilterChainDefinitionMap(chains);
        return bean;
    }

    /**
     * @return
     * @see org.apache.shiro.mgt.SecurityManager
     */
    @Bean(name = "securityManager")
    public DefaultWebSecurityManager securityManager() {
        DefaultWebSecurityManager manager = new DefaultWebSecurityManager();
        manager.setRealm(userRealm());
        manager.setCacheManager(redisCacheManager());
        manager.setSessionManager(defaultWebSessionManager());
        return manager;
    }

    /**
     * @return
     * @see DefaultWebSessionManager
     */
    @Bean(name = "sessionManager")
    public DefaultWebSessionManager defaultWebSessionManager() {
        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
        sessionManager.setCacheManager(redisCacheManager());
        sessionManager.setGlobalSessionTimeout(1800000);
        sessionManager.setDeleteInvalidSessions(true);
        sessionManager.setSessionValidationSchedulerEnabled(true);
        sessionManager.setDeleteInvalidSessions(true);
        return sessionManager;
    }

    /**
     * @return
     * @see UserRealm--->AuthorizingRealm
     */
    @Bean
    @DependsOn(value = {"lifecycleBeanPostProcessor", "shrioRedisCacheManager"})
    public UserRealm userRealm() {
        UserRealm userRealm = new UserRealm();
        userRealm.setCacheManager(redisCacheManager());
        userRealm.setCachingEnabled(true);
        userRealm.setAuthenticationCachingEnabled(true);
        userRealm.setAuthorizationCachingEnabled(true);
        return userRealm;
    }

    @Bean
    public URLPermissionsFilter urlPermissionsFilter() {
        return new URLPermissionsFilter();
    }

    @Bean(name = "shrioRedisCacheManager")
    @DependsOn(value = "redisTemplate")
    public ShrioRedisCacheManager redisCacheManager() {
        ShrioRedisCacheManager cacheManager = new ShrioRedisCacheManager(redisTemplate());
        return cacheManager;
    }

    @Bean(name = "redisTemplate")
    public RedisTemplate<byte[], Object> redisTemplate() {
        RedisTemplate<byte[], Object> template = new RedisTemplate<byte[], Object>();
        template.setConnectionFactory(connectionFactory());
        return template;
    }

    @Bean
    public JedisConnectionFactory connectionFactory() {
        JedisConnectionFactory conn = new JedisConnectionFactory();
        conn.setDatabase(RedisConfig.database);
        conn.setHostName(RedisConfig.host);
        conn.setPort(RedisConfig.port);
        conn.setTimeout(3000);
        return conn;
    }

    @Bean
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }
}