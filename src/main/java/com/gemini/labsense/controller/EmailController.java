package com.gemini.labsense.controller;

import com.gemini.labsense.common.result.Result;
import com.gemini.labsense.manager.EmailManager;
import com.gemini.labsense.pojo.dto.MailCodeVerifyDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/email")
@Slf4j
public class EmailController {
    private final EmailManager emailManager;

    /**
     * 发送验证码
     *
     * @param email 邮箱
     * @return 发送结果
     */
    @GetMapping("/code/{email}")
    public Result<?> sendVerifyCode(@PathVariable String email) {
        emailManager.sendVerifyCode(email);
        return Result.ok();
    }


    /**
     * 验证码校验
     *
     * @param mailCodeVerifyDto 校验参数
     * @return 发送结果
     */
    @PostMapping("/code/verification")
    public Result<?> verifyCode(@RequestBody @Valid MailCodeVerifyDto mailCodeVerifyDto) {
        emailManager.verifyCode(mailCodeVerifyDto.getEmail(), mailCodeVerifyDto.getCode());
        return Result.ok();
    }
}
