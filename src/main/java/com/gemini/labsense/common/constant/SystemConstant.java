package com.gemini.labsense.common.constant;

import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class SystemConstant {
    private SystemConstant() {
        throw new IllegalStateException("非法实例化常量类");
    }

    // 系统TOKEN名称
    public static final String AUTH_TOKEN_PARAM = "Authorization";

    // 默认时区
    public static final String ZONE_ID = "UTC+8";
    // 默认时间格式
    public static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String DATE_FORMAT = "yyyy-MM-dd";
    public static final String TIME_FORMAT = "HH:mm:ss";
    public static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(DATE_TIME_FORMAT);
    public static final SimpleModule SIMPLE_MODULE = new SimpleModule()
            .addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(DateTimeFormatter.ofPattern(SystemConstant.DATE_TIME_FORMAT)))
            .addDeserializer(LocalDate.class, new LocalDateDeserializer(DateTimeFormatter.ofPattern(SystemConstant.DATE_FORMAT)))
            .addDeserializer(LocalTime.class, new LocalTimeDeserializer(DateTimeFormatter.ofPattern(SystemConstant.TIME_FORMAT)))
            .addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(DateTimeFormatter.ofPattern(SystemConstant.DATE_TIME_FORMAT)))
            .addSerializer(LocalDate.class, new LocalDateSerializer(DateTimeFormatter.ofPattern(SystemConstant.DATE_FORMAT)))
            .addSerializer(LocalTime.class, new LocalTimeSerializer(DateTimeFormatter.ofPattern(SystemConstant.TIME_FORMAT)));

    // 正则表达式
    public static final String SYSTEM_CONFIG_KEY_PASSWORD_REGEX = "password_regex";
    public static final String EMAIL_REGEX = "^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$";
    public static final String PHONE_REGEX = "^1[3-9]\\d{9}$";

    // 邮件参数
    public static final String MAIL_TITLE = "【LabSense】你收到了一个邮箱验证码";
    public static final String MAIL_TEMPLATE = "verify_code.html";
    public static final String MAIL_FROM_NAME = "LabSense";
    public static final String MAIL_CODE_PARAM = "code";


}
