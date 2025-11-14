package com.gemini.labsense.common.security;

import com.gemini.labsense.common.result.Result;
import com.gemini.labsense.common.result.ResultCodeEnum;
import com.gemini.labsense.common.utils.ResponseUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;


/**
 * 未认证访问权限资源处理器
 */
@Component
public class LabsenceAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) {
        ResponseUtil.out(response, Result.build(null, ResultCodeEnum.LOGIN_AUTH));
    }
}
