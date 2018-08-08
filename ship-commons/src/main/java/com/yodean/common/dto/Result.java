package com.yodean.common.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;

/**
 * Created by rick on 7/16/18.
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Result<T> implements Serializable {

    private Boolean success;

    private Integer code;

    private String msg;

    private T data;

}
