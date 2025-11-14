package com.gemini.labsense.common.security;

import com.gemini.labsense.common.result.ResultCodeEnum;
import com.gemini.labsense.pojo.entity.User;
import com.gemini.labsense.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;


@Slf4j
@Component
@RequiredArgsConstructor
public class LabSenseUserDetailsService implements UserDetailsService {
    private final UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.getByUsername(username);
        if (null == user) {
            throw new UsernameNotFoundException("用户名不存在！");
        }
        if (user.getStatus() != 1) {
            throw new DisabledException(ResultCodeEnum.ACCOUNT_STOP.getMessage());
        }
        List<String> userPermsList = userService.findUserPermsList(user.getId());
        user.setUserPermsList(userPermsList);
        return new LabSenseUser(user);
    }
}