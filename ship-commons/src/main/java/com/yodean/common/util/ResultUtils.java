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
        Result<T> result = new Result();
        result.setData(data);
        result.setCode(ResultCode.SUCCESS.getCode());
        result.setMsg(ResultCode.SUCCESS.getMessage());
        return result;
    }

    public static Result<String> error(String msg) {
        return error(ResultCode.UNKNOW_ERROR.getCode(), msg);
    }

    public static Result<String> error(Integer code, String msg) {
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
        return result;
    }
}
