package com.yodean.oa.module.asset;

import lombok.Getter;

/**
 * Created by rick on 7/20/18.
 */
@Getter
public enum AssetStatus {
    NORMAL("归还"), FORBIDDEN("禁用"), BORROWING("领用");

    private String description;
    AssetStatus(String description) {
        this.description = description;
    }

}
