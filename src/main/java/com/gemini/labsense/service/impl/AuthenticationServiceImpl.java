package com.gemini.labsense.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.gemini.labsense.common.constant.enums.UserStatus;
import com.gemini.labsense.common.exception.LabSenseException;
import com.gemini.labsense.common.result.ResultCodeEnum;
import com.gemini.labsense.manager.UserManager;
import com.gemini.labsense.mapper.UserMapper;
import com.gemini.labsense.pojo.dto.AuthPasswordUpdateDto;
import com.gemini.labsense.pojo.dto.AuthRegisterDto;
import com.gemini.labsense.pojo.entity.User;
import com.gemini.labsense.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserManager userManager;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    /**
     * 注册
     *
     * @param authRegisterDto 注册参数
     */
    @Override
    public void register(AuthRegisterDto authRegisterDto) {
        User user = BeanUtil.copyProperties(authRegisterDto, User.class);
        // 校验数据
        userManager.check(user);
        // 密码加密注册
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setStatus(UserStatus.WAIT_AUDIT.getCode());
        userMapper.insert(user);

    }

    /**
     * 修改密码
     *
     * @param authPasswordUpdateDto 修改密码参数
     */
    @Override
    public void updatePassword(AuthPasswordUpdateDto authPasswordUpdateDto) {
        // 获取用户
        User user = userMapper.selectOne(new LambdaQueryWrapper<User>().eq(User::getEmail, authPasswordUpdateDto.getEmail()));
        if (user != null) {
            // 校验数据
            userManager.patternPassword(authPasswordUpdateDto.getNewPassword());
            user.setPassword(passwordEncoder.encode(authPasswordUpdateDto.getNewPassword()));
            userMapper.updateById(user);
        } else {
            throw new LabSenseException(ResultCodeEnum.ARGUMENT_VALID_ERROR.getCode(), "该邮箱未被绑定");
        }
    }
}
