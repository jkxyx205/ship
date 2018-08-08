package com.yodean.common.enums;

/**
 * Created by rick on 7/16/18.
 */
public enum ResultCode {
    UNKNOWN_ERROR(-1, "未知错误"),
    SUCCESS(200, "成功"),
    VALIDATE_ERROR(50001, "验证错误");

    private Integer code;

    private String message;

    ResultCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
