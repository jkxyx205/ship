package com.yodean.oa.common.exception;

import com.yodean.oa.common.plugin.document.enums.ExceptionCode;

/**
 * Created by rick on 7/18/18.
 */
public class OAException extends RuntimeException {
    private Integer code;

    private String msg;

    private Exception exception;

    public OAException(ExceptionCode exceptionCode) {
        super(exceptionCode.getMessage());
        this.code = exceptionCode.getCode();
        this.msg = exceptionCode.getMessage();
    }

    public OAException(ExceptionCode exceptionCode, Exception exception) {
        super(exceptionCode.getMessage());
        this.code = exceptionCode.getCode();
        this.msg = exceptionCode.getMessage();
        this.exception = exception;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Exception getException() {
        return exception;
    }

    public void setException(Exception exception) {
        this.exception = exception;
    }
}
