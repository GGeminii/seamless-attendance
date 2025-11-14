package com.gemini.labsense.common.constant.enums;

import lombok.Getter;

@Getter
public enum UserStatus {
    NORMAL("正常", 1),
    DISABLE("禁用", 2),
    WAIT_AUDIT("待审核", 0);

    private final String status;
    private final Integer code;

    UserStatus(String status, Integer code) {
        this.status = status;
        this.code = code;
    }
}
