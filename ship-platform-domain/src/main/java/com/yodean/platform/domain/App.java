package com.yodean.platform.domain;

import com.yodean.common.domain.EntityData;
import lombok.Data;

/**
 * Created by rick on 7/13/18.
 */
@Data
//@Entity
//@Table(name = "sys_app")
public class App extends EntityData {

    private String name;

    private String appKey;

    private String appSecret;

    private Boolean available;
}
