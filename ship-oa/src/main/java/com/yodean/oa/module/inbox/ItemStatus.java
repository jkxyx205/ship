package com.yodean.oa.module.inbox;

/**
 * Created by rick on 7/18/18.
 */
public enum ItemStatus {
    INBOX("待办"),
    ARCHIVE("归档"),
    TRASH("回收站"),
    CLEAN("回收站删除");
    private String description;

    private ItemStatus(String description) {
        this.description = description;
    }
}
