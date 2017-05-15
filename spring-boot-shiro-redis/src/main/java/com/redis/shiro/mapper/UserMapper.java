package com.redis.shiro.mapper;

import com.redis.shiro.domain.UserInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {

    @Select("select *from t_user where account=#{account}")
    UserInfo findByAccount(String account);
}
