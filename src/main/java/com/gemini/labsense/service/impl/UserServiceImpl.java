package com.gemini.labsense.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gemini.labsense.mapper.UserMapper;
import com.gemini.labsense.pojo.entity.User;
import com.gemini.labsense.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    private final UserMapper userMapper;


    @Override
    public User getByUsername(String username) {
        return this.lambdaQuery().eq(User::getUsername, username).one();
    }

    @Override
    public List<String> findUserPermsList(Long id) {
        return userMapper.findUserPermsList(id);
    }
}
