package com.gemini.labsense.common.result;

import lombok.Getter;

/**
 * 统一返回结果状态信息类
 */
@Getter
public enum ResultCodeEnum {

    SUCCESS(200, "成功"),
    FAIL(500, "服务异常"),
    EMAIL_ERROR(510, "发送邮件失败"),

    LOGIN_AUTH(401, "请先登陆再访问"),
    ACCOUNT_STOP(403, "账号未审核通过或已停用"),
    LOGIN_ERROR(403, "账号或密码错误"),
    PERMISSION(403, "你没有权限访问"),
    ARGUMENT_VALID_ERROR(405, "参数校验异常"),
    MAIL_CODE_ERROR(406, "邮箱验证码错误"),
    REPEAT_SUBMIT(407, "重复提交"),


    SERVICE_ERROR(2012, "服务异常"),
    DATA_ERROR(204, "数据异常"),
    ILLEGAL_REQUEST(205, "非法请求"),
    FEIGN_FAIL(207, "远程调用失败"),
    UPDATE_ERROR(204, "数据更新失败"),

    SIGN_ERROR(300, "签名错误"),
    SIGN_OVERDUE(301, "签名已过期"),
    VALIDATE_CODE_ERROR(218, "验证码错误"),

    PHONE_CODE_ERROR(215, "手机验证码不正确");

    private final Integer code;

    private final String message;

    ResultCodeEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
