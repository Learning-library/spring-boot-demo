package com.redis.shiro.service;

import java.util.List;

import com.redis.shiro.domain.ModuleInfo;

public interface ModuleService {
    /**
     * 获取角色模块
     *
     * @param userId
     * @return
     */
    List<ModuleInfo> findModuleByUserId(int userId);
}
