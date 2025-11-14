package com.gemini.labsense.service;

import com.gemini.labsense.pojo.dto.AuthPasswordUpdateDto;
import com.gemini.labsense.pojo.dto.AuthRegisterDto;
import jakarta.validation.Valid;

public interface AuthenticationService {
    /**
     * 注册
     *
     * @param authRegisterDto 注册参数
     */
    void register(@Valid AuthRegisterDto authRegisterDto);

    /**
     * 修改密码
     *
     * @param authPasswordUpdateDto 修改密码参数
     */
    void updatePassword(@Valid AuthPasswordUpdateDto authPasswordUpdateDto);
}
