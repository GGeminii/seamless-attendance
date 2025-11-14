package com.gemini.labsense.manager;


import cn.hutool.core.util.RandomUtil;
import com.gemini.labsense.common.constant.CacheConstant;
import com.gemini.labsense.common.constant.SystemConstant;
import com.gemini.labsense.common.exception.LabSenseException;
import com.gemini.labsense.common.result.ResultCodeEnum;
import com.gemini.labsense.common.utils.EmailSender;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.thymeleaf.context.Context;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
public class EmailManager {
    @Value("${spring.mail.username}")
    private String from;
    private final EmailSender emailSender;
    private final RedisTemplate<Object, Object> redisTemplate;

    /**
     * 发送验证码
     *
     * @param email 邮箱
     */
    public void sendVerifyCode(String email) {
        // 验证是否重复发送
        if (redisTemplate.hasKey(CacheConstant.MAIL_VERIFY_CODE_KEY_PREFIX + email)) {
            throw new LabSenseException(ResultCodeEnum.REPEAT_SUBMIT.getCode(), "请勿重复发送验证码");
        }
        // 随机生成验证码6位
        String code = String.valueOf(RandomUtil.getRandom().nextInt(100000, 999999));
        Context context = new Context();
        context.setVariables(Map.of(SystemConstant.MAIL_CODE_PARAM, code));
        // 存入REDIS缓存-5分钟
        redisTemplate.opsForValue().set(CacheConstant.MAIL_VERIFY_CODE_KEY_PREFIX + email, code, CacheConstant.MAIL_VERIFY_CODE_KEY_TIMEOUT, TimeUnit.SECONDS);
        // 发送邮件
        emailSender.sendEmailWithTemplate(
                EmailSender.Email.builder()
                        .context(context)
                        .template(SystemConstant.MAIL_TEMPLATE)
                        .from(this.from)
                        .fromName(SystemConstant.MAIL_FROM_NAME)
                        .to(email)
                        .subject(SystemConstant.MAIL_TITLE)
                        .build(),
                Collections.emptyList()
        );
    }

    /**
     * 验证码验证
     *
     * @param email 邮箱
     * @param code  验证码
     */
    public void verifyCode(String email, String code) {
        String redisCode = (String) redisTemplate.opsForValue().get(CacheConstant.MAIL_VERIFY_CODE_KEY_PREFIX + email);
        if (redisCode != null && redisCode.equals(code)) {
            redisTemplate.delete(CacheConstant.MAIL_VERIFY_CODE_KEY_PREFIX + email);
        } else {
            throw new LabSenseException(ResultCodeEnum.MAIL_CODE_ERROR);
        }
    }
}
