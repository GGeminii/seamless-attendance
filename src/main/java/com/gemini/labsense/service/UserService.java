package com.gemini.labsense.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gemini.labsense.pojo.entity.User;

import java.util.List;

public interface UserService extends IService<User> {
    /**
     * 根据用户名查询用户信息
     *
     * @param username 用户名
     * @return 用户信息
     */
    User getByUsername(String username);

    /**
     * 根据用户id查询权限列表
     *
     * @param id 用户id
     * @return 权限列表
     */
    List<String> findUserPermsList(Long id);
}
