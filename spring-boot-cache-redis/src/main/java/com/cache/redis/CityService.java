package com.cache.redis;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

@Component
@CacheConfig(cacheNames = "CityService")
public class CityService {
    Logger logger = LogManager.getLogger(getClass());

    @Cacheable(keyGenerator = "wiselyKeyGenerator")//使用自定义key
    public CityInfo getCity(int id, String city) {
        logger.info("id: {}, city: {}", id, city);
        return new CityInfo(id, city);
    }

    @Cacheable(key = "#id + '_'  + #city")//放入缓存
    public CityInfo getCity1(int id, String city) {
        logger.info("id: {}, city: {}", id, city);
        return new CityInfo(id, city);
    }


    @CachePut(key = "#id + '_'  + #city")//刷新缓存
    public void addCity(int id, String city) {
        logger.info("id: {}, city: {}", id, city);
        new CityInfo(id, city);
    }


}
