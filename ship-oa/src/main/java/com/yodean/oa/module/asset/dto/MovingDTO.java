package com.yodean.oa.module.asset.dto;

import com.yodean.oa.module.asset.material.entity.Moving;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * Created by rick on 7/24/18.
 */
@Data
public class MovingDTO implements Serializable {

    private Long srcStorage;

    private Long distStorage;

    private List<Moving> movingList;

}
