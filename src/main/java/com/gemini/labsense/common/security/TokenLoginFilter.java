package com.gemini.labsense.common.security;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.gemini.labsense.common.constant.CacheConstant;
import com.gemini.labsense.common.constant.SystemConstant;
import com.gemini.labsense.common.exception.LabSenseException;
import com.gemini.labsense.common.result.Result;
import com.gemini.labsense.common.result.ResultCodeEnum;
import com.gemini.labsense.common.utils.ResponseUtil;
import com.gemini.labsense.pojo.dto.AuthLoginDto;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.servlet.util.matcher.PathPatternRequestMatcher;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * 登录过滤器，继承UsernamePasswordAuthenticationFilter，对用户名密码进行登录校验
 */
public class TokenLoginFilter extends UsernamePasswordAuthenticationFilter {
    private final RedisTemplate<Object, Object> redisTemplate;
    private final ObjectMapper objectMapper;

    public TokenLoginFilter(AuthenticationManager authenticationManager, RedisTemplate<Object, Object> redisTemplate, ObjectMapper objectMapper) {
        this.setAuthenticationManager(authenticationManager);
        this.setPostOnly(false);
        //指定登录接口及提交方式
        this.setRequiresAuthenticationRequestMatcher(
                PathPatternRequestMatcher.withDefaults()
                        .matcher(HttpMethod.POST, "/api/*/auth/login")
        );
        this.redisTemplate = redisTemplate;
        this.objectMapper = objectMapper;
    }

    /**
     * 登录认证
     *
     * @param req 请求
     * @param res 响应
     * @return Authentication
     * @throws AuthenticationException 认证异常
     */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res) throws AuthenticationException {
        try {
            AuthLoginDto loginVo = objectMapper.readValue(req.getInputStream(), AuthLoginDto.class);
            Authentication authenticationToken = new UsernamePasswordAuthenticationToken(loginVo.getUsername(), loginVo.getPassword());
            return this.getAuthenticationManager().authenticate(authenticationToken);
        } catch (IOException e) {
            logger.error("登录失败：" + e.getMessage());
            throw new LabSenseException(e);
        }
    }

    /**
     * 登录成功
     *
     * @param request  请求
     * @param response 响应
     * @param chain    过滤器链
     * @param auth     认证信息
     */
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication auth) {
        LabSenseUser labSenseUser = (LabSenseUser) auth.getPrincipal();
        String token = UUID.randomUUID().toString().replace("-", "");
        redisTemplate.opsForValue().set(CacheConstant.AUTH_LOGIN_KEY_PREFIX + token, labSenseUser.getUser(), CacheConstant.AUTH_LOGIN_KEY_TIMEOUT, TimeUnit.SECONDS);
        // 记录日志
        logger.info("用户登录成功：" + labSenseUser.getUsername());
        // 登录成功返回token
        Map<String, Object> map = new HashMap<>();
        map.put(SystemConstant.AUTH_TOKEN_PARAM, token);
        ResponseUtil.out(response, Result.ok(map));
    }

    /**
     * 登录失败
     *
     * @param request  请求
     * @param response 响应
     * @param e        认证异常
     */
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) {
        if (e.getCause() instanceof RuntimeException) {
            ResponseUtil.out(response, Result.build(null, 403, e.getCause().getMessage()));
        } else {
            ResponseUtil.out(response, Result.build(null, 403, ResultCodeEnum.LOGIN_ERROR.getMessage()));
        }
    }
}
