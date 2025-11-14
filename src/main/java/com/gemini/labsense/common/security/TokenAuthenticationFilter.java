package com.gemini.labsense.common.security;

import cn.hutool.core.collection.CollUtil;
import com.gemini.labsense.common.constant.CacheConstant;
import com.gemini.labsense.common.constant.SystemConstant;
import com.gemini.labsense.common.result.Result;
import com.gemini.labsense.common.result.ResultCodeEnum;
import com.gemini.labsense.common.utils.AuthContextHolder;
import com.gemini.labsense.common.utils.ResponseUtil;
import com.gemini.labsense.pojo.entity.User;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 身份验证过滤器
 */
@Component
@RequiredArgsConstructor
public class TokenAuthenticationFilter extends OncePerRequestFilter {
    private final RedisTemplate<Object, Object> redisTemplate;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        //如果是登录接口或非admin大头的直接放行
        String uri = request.getRequestURI();
        AntPathMatcher antPathMatcher = new AntPathMatcher();
        if (antPathMatcher.match("/api/*/auth/login", uri) ||
                antPathMatcher.match("/api/*/auth/register", uri) ||
                antPathMatcher.match("/swagger-resources/**", uri) ||
                antPathMatcher.match("/webjars/**", uri) ||
                antPathMatcher.match("/v3/**", uri) ||
                antPathMatcher.match("/doc.html", uri) ||
                antPathMatcher.match("/favicon.ico", uri)) {
            chain.doFilter(request, response);
            return;
        }

        UsernamePasswordAuthenticationToken authentication = getAuthentication(request);
        if (null != authentication) {
            SecurityContextHolder.getContext().setAuthentication(authentication);
            chain.doFilter(request, response);
        } else {
            ResponseUtil.out(response, Result.build(null, ResultCodeEnum.PERMISSION));
        }
    }

    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
        // token置于header里
        String token = request.getHeader(SystemConstant.AUTH_TOKEN_PARAM);
        logger.info("登录用户token:" + token);
        if (StringUtils.hasText(token)) {
            User user = (User) redisTemplate.opsForValue().get(CacheConstant.AUTH_LOGIN_KEY_PREFIX + token);
            logger.info("登录用户:" + user);
            UsernamePasswordAuthenticationToken auth;
            if (null != user) {
                if (!CollUtil.isEmpty(user.getUserPermsList())) {
                    List<SimpleGrantedAuthority> authorities = user.toGrantedAuthorities();
                    auth = new UsernamePasswordAuthenticationToken(user.getUsername(), null, authorities);
                } else {
                    auth = new UsernamePasswordAuthenticationToken(user.getUsername(), null, new ArrayList<>());
                }
                // 获取用户登录凭证，将用户信息保存到SecurityContextHolder(ThreadLocal)
                AuthContextHolder.setUserId(user.getId());
                SecurityContextHolder.getContext().setAuthentication(auth);
                return auth;
            }
        }
        return null;
    }
}
