package com.yodean.common.enums;

import com.google.common.collect.Maps;

import java.util.Map;

/**
 * Created by rick on 7/17/18.
 */
public enum DelFlag {
    DEL_FLAG_REMOVE, DEL_FLAG_NORMAL, DEL_FLAG_CLEAN;

    private static Map<Integer, DelFlag> mapValue = Maps.newHashMapWithExpectedSize(3);

    static {
        for (DelFlag flag : DelFlag.values()) {
            mapValue.put(flag.ordinal(), flag);
        }
    }

    public static DelFlag getEnumById(int id) {
        return mapValue.get(id);
    }
}

