package com.gemini.labsense.common.exception;

import com.gemini.labsense.common.result.Result;
import com.gemini.labsense.common.result.ResultCodeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 全局异常处理类
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(LabSenseException.class)
    @ResponseBody
    public Result<?> labSenseException(LabSenseException e) {
        e.printStackTrace();
        return Result.build(null, e.getCode(), e.getMessage());
    }

    @ExceptionHandler({IllegalArgumentException.class})
    @ResponseBody
    public Result<?> illegalArgumentException(IllegalArgumentException e) {
        return Result.build(null, ResultCodeEnum.ARGUMENT_VALID_ERROR);
    }

    @ExceptionHandler(value = BindException.class)
    @ResponseBody
    public Result<?> bindException(BindException exception) {
        return getArgumentNotValidResult(exception.getBindingResult());
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    @ResponseBody
    public Result<?> methodArgumentNotValidException(MethodArgumentNotValidException exception) {
        return getArgumentNotValidResult(exception.getBindingResult());
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Result<?> error(Exception e) {
        e.printStackTrace();
        return Result.fail();
    }

    private Result<?> getArgumentNotValidResult(BindingResult bindingResult) {
        Map<String, Object> errorMap = new HashMap<>();
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        fieldErrors.forEach(error -> {
            log.error("field: {}, msg:{}", error.getField(), error.getDefaultMessage());
            errorMap.put(error.getField(), error.getDefaultMessage());
        });
        return Result.build(errorMap, ResultCodeEnum.ARGUMENT_VALID_ERROR);
    }
}
