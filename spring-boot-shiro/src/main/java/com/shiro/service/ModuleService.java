package com.shiro.service;

import java.util.List;

import com.shiro.domain.ModuleInfo;

public interface ModuleService {
    /**
     * 获取角色模块
     *
     * @param userId
     * @return
     */
    List<ModuleInfo> findModuleByUserId(int userId);
}
