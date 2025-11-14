package com.gemini.labsense.common.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gemini.labsense.common.exception.LabSenseException;
import com.gemini.labsense.common.result.Result;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class ResponseUtil {
    private ResponseUtil() {
        throw new IllegalStateException("非法实例化常量类");
    }

    public static void out(HttpServletResponse response, Result<?> r) {
        ObjectMapper mapper = new ObjectMapper();
        response.setStatus(HttpStatus.OK.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding(StandardCharsets.UTF_8.toString());
        try {
            mapper.writeValue(response.getWriter(), r);
        } catch (IOException e) {
            throw new LabSenseException(e);
        }
    }
}
