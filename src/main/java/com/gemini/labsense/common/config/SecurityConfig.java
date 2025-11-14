package com.gemini.labsense.common.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gemini.labsense.common.security.LabsenceAuthenticationEntryPoint;
import com.gemini.labsense.common.security.TokenAuthenticationFilter;
import com.gemini.labsense.common.security.TokenLoginFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true, jsr250Enabled = true)
@RequiredArgsConstructor
public class SecurityConfig {

    private final UserDetailsService userDetailsService;

    private final LabsenceAuthenticationEntryPoint labsenceAuthenticationEntryPoint;

    private final AuthenticationConfiguration authenticationConfiguration;

    private final TokenAuthenticationFilter tokenAuthenticationFilter;

    private final RedisTemplate<Object, Object> redisTemplate;

    private final ObjectMapper objectMapper;


    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        // 创建一个用户认证提供者, 设置用户相信信息，可以从数据库中读取、或者缓存、或者配置文件
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider(userDetailsService);
        // 设置加密机制，若想要尝试对用户进行身份验证，我们需要知道使用的是什么编码
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                // 禁用csrf(防止跨站请求伪造攻击)
                .csrf(AbstractHttpConfigurer::disable)
                // 禁用缓存
                .sessionManagement(sessionManagement ->
                        // 使用无状态session，即不使用session缓存数据
                        sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                //未认证访问权限资源处理器
                .exceptionHandling(exceptionHandling ->
                        exceptionHandling.authenticationEntryPoint(labsenceAuthenticationEntryPoint)
                )
                // 设置白名单
                .authorizeHttpRequests(authorizeHttpRequests ->
                        authorizeHttpRequests.requestMatchers(
                                        "/api/*/auth/login",
                                        "/api/*/auth/register"
                                ).permitAll()
                                .requestMatchers(
                                        "/favicon.ico",
                                        "/swagger-resources/**",
                                        "/webjars/**",
                                        "/v3/**",
                                        "/doc.html"
                                ).permitAll()
                                .anyRequest()
                                .authenticated() // 对于其他任何请求，都保护起来
                )
                .authenticationProvider(authenticationProvider())
                // 添加token过滤器
                .addFilterBefore(tokenAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilter(new TokenLoginFilter(authenticationManager(authenticationConfiguration), redisTemplate, objectMapper));
        return http.build();
    }

}