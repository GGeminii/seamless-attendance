package com.gemini.labsense.controller.managermant;

import com.gemini.labsense.common.result.Result;
import com.gemini.labsense.pojo.dto.AuthPasswordUpdateDto;
import com.gemini.labsense.pojo.dto.AuthRegisterDto;
import com.gemini.labsense.service.AuthenticationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/m/auth")
@RequiredArgsConstructor
public class ManAuthenticationController {
    private final AuthenticationService authenticationService;

    /**
     * 注册
     *
     * @param authRegisterDto 注册参数
     * @return 注册结果
     */
    @PostMapping("/register")
    public Result<?> register(@RequestBody @Valid AuthRegisterDto authRegisterDto) {
        authenticationService.register(authRegisterDto);
        return Result.ok();
    }

    /**
     * 修改密码
     *
     * @param authPasswordUpdateDto 修改密码参数
     * @return 修改密码结果
     */
    @PutMapping("/password")
    public Result<?> updatePassword(@RequestBody @Valid AuthPasswordUpdateDto authPasswordUpdateDto) {
        authenticationService.updatePassword(authPasswordUpdateDto);
        return Result.ok();
    }
}
