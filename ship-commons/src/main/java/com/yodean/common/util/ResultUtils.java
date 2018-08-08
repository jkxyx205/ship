package com.yodean.common.util;

import com.yodean.common.dto.Result;
import com.yodean.common.enums.ResultCode;

/**
 * Created by rick on 7/16/18.
 */
public final class ResultUtils {

    public static Result success() {
        return success(null);
    }

    public static <T> Result<T> success(T data) {
        return success(data, ResultCode.SUCCESS.getMessage());
    }

    public static <T> Result<T> success(T data, String msg) {
        return success(data, ResultCode.SUCCESS.getCode(), msg);
    }

    public static <T> Result<T> success(T data, ResultCode resultCode) {
        return success(data, resultCode.getCode(), resultCode.getMessage());
    }

    public static <T> Result<T> success(T data, Integer code, String msg) {
        Result<T> result = new Result();
        result.setCode(code);
        result.setMsg(msg);
        result.setData(data);
        result.setSuccess(true);
        return result;
    }


    public static Result<String> error(String msg) {
        return error(msg, ResultCode.UNKNOWN_ERROR.getCode());
    }

    public static Result<String> error(String msg, Integer code) {
        return error(code, msg, null);
    }

    public static Result<String> error(ResultCode resultCode) {
        return error(resultCode.getCode(), resultCode.getMessage(), null);
    }

    public static <T> Result<T> error(ResultCode resultCode, T data) {
        return error(resultCode.getCode(), resultCode.getMessage(), data);
    }

    public static <T> Result<T> error(Integer code, String msg, T data) {
        Result<T> result = new Result();
        result.setCode(code);
        result.setMsg(msg);
        result.setData(data);
        result.setSuccess(false);
        return result;
    }
}
