package com.shiro.mapper;

import org.apache.ibatis.annotations.Select;

import com.shiro.domain.UserInfo;

public interface UserMapper {

    @Select("select *from t_user where account=#{account}")
    UserInfo findByAccount(String account);
}
