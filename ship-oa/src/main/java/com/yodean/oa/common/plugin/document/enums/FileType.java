package com.yodean.oa.common.plugin.document.enums;

/**
 * Created by rick on 2018/3/22.
 */
public enum FileType {
    FOLDER("目录"),
    FILE("文件");
    private String description;

    FileType(String description) {
        this.description = description;
    }

}
