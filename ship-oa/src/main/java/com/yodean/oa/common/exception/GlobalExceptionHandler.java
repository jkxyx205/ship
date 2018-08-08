package com.yodean.oa.common.exception;

import com.yodean.common.dto.Result;
import com.yodean.common.enums.ResultCode;
import com.yodean.common.util.ResultUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

/**
 * Created by rick on 7/19/18.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(value = Exception.class)
    public Object handler(Exception e) {
        logger.error("server catches exception {}", e);

        Result<String> result;

        if (e instanceof  OAException) {
            OAException ex = (OAException)e;
            result = ResultUtils.error(ex.getMessage(), ex.getCode());

        } else if (e instanceof MaxUploadSizeExceededException) {
            result = ResultUtils.error(ResultCode.VALIDATE_ERROR, "文件不能超过5M");
        } else {
            result = ResultUtils.error(e.getMessage());
        }

        return result;
    }

}
