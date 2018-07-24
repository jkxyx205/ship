package com.yodean.oa.common.exception;

import com.yodean.oa.common.plugin.document.enums.ExceptionCode;
import lombok.Data;

/**
 * Created by rick on 7/18/18.
 */
@Data
public class OAException extends RuntimeException {

    private ExceptionCode exceptionCode;

    private Integer code;

    private String msg;

    private Exception exception;



    public OAException(ExceptionCode exceptionCode) {
        this(exceptionCode, exceptionCode.getMessage(), null);
    }

    public OAException(ExceptionCode exceptionCode, String message) {
        this(exceptionCode, message, null);
    }


    public OAException(ExceptionCode exceptionCode, Exception exception) {
        this(exceptionCode, exceptionCode.getMessage(), exception);
    }

    public OAException(ExceptionCode exceptionCode, String message, Exception exception) {
        super(message);
        this.exceptionCode =exceptionCode;
        this.exception = exception;
        this.code = exceptionCode.getCode();
        this.msg = message;
    }

}
