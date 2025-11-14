package com.gemini.labsense.manager;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.gemini.labsense.common.constant.SystemConstant;
import com.gemini.labsense.common.exception.LabSenseException;
import com.gemini.labsense.common.result.ResultCodeEnum;
import com.gemini.labsense.mapper.RoleMapper;
import com.gemini.labsense.mapper.SystemConfigMapper;
import com.gemini.labsense.mapper.UserMapper;
import com.gemini.labsense.pojo.entity.Role;
import com.gemini.labsense.pojo.entity.SystemConfig;
import com.gemini.labsense.pojo.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserManager {
    private final SystemConfigMapper systemConfigMapper;
    private final UserMapper userMapper;
    private final RoleMapper roleMapper;

    public void check(User user) {
        if (existEmail(user.getEmail())) {
            throw new LabSenseException(ResultCodeEnum.ARGUMENT_VALID_ERROR.getCode(), "邮箱已存在");
        } else if (existPhone(user.getPhone())) {
            throw new LabSenseException(ResultCodeEnum.ARGUMENT_VALID_ERROR.getCode(), "手机号已存在");
        } else if (existUsername(user.getUsername())) {
            throw new LabSenseException(ResultCodeEnum.ARGUMENT_VALID_ERROR.getCode(), "用户名已存在");
        } else if (!patternPassword(user.getPassword())) {
            throw new LabSenseException(ResultCodeEnum.ARGUMENT_VALID_ERROR.getCode(), "密码应包含8位以上含字母、数字、特殊字符");
        } else if (!existRole(user.getRoleId())) {
            throw new LabSenseException(ResultCodeEnum.ARGUMENT_VALID_ERROR.getCode(), "角色不存在");
        }
    }

    public boolean existEmail(String email) {
        return userMapper.exists(new LambdaQueryWrapper<User>().eq(User::getEmail, email));
    }

    public boolean existPhone(String phone) {
        // 校验格式与唯一性
        return userMapper.exists(new LambdaQueryWrapper<User>().eq(User::getPhone, phone));
    }

    public boolean existUsername(String username) {
        // 校验唯一性
        return userMapper.exists(new LambdaQueryWrapper<User>().eq(User::getUsername, username));
    }

    public boolean patternPassword(String password) {
        // 获取密码正则
        String regex = systemConfigMapper.selectOne(
                new LambdaQueryWrapper<SystemConfig>()
                        .eq(SystemConfig::getConfigKey, SystemConstant.SYSTEM_CONFIG_KEY_PASSWORD_REGEX)
        ).getConfigValue();
        return password.matches(regex);
    }

    public boolean existRole(Integer roleId) {
        // 校验是否存在
        return roleMapper.exists(new LambdaQueryWrapper<Role>().eq(Role::getId, roleId));
    }
}
