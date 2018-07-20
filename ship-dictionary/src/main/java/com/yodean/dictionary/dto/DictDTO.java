package com.yodean.dictionary.dto;

import lombok.Data;

/**
 * Created by rick on 7/20/18.
 */
@Data
public class DictDTO {

    private String category;

    private String name;

    private String description;

    private String seq;

    private Long parentId;
}
