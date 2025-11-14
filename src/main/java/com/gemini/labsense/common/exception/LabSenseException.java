package com.gemini.labsense.common.exception;

import com.gemini.labsense.common.result.ResultCodeEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * 自定义全局异常类
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ToString
public class LabSenseException extends RuntimeException {

    private final Integer code;

    private final String message;

    /**
     * 接受异常
     *
     * @param e 异常
     */
    public LabSenseException(Exception e) {
        super(e);
        this.code = ResultCodeEnum.FAIL.getCode();
        this.message = e.getMessage();
    }
    
    /**
     * 通过状态码和错误消息创建异常对象
     *
     * @param code    状态码
     * @param message 错误消息
     */
    public LabSenseException(Integer code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }

    /**
     * 接收枚举类型对象
     *
     * @param resultCodeEnum 枚举对象
     */
    public LabSenseException(ResultCodeEnum resultCodeEnum) {
        super(resultCodeEnum.getMessage());
        this.code = resultCodeEnum.getCode();
        this.message = resultCodeEnum.getMessage();
    }
}
