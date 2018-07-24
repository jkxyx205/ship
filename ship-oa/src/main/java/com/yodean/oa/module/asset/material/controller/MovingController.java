package com.yodean.oa.module.asset.material.controller;

import com.yodean.common.dto.Result;
import com.yodean.common.util.ResultUtils;
import com.yodean.oa.module.asset.dto.MovingDTO;
import com.yodean.oa.module.asset.material.service.MovingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by rick on 7/24/18.
 */
@RestController
@RequestMapping("/materials/moving")
public class MovingController {

    @Autowired
    private MovingService movingServive;

    /**
     * 移库
     * @return
     */
    @PostMapping
    public Result moving(@RequestBody MovingDTO movingDTO) {
        movingServive.moving(movingDTO);
        return ResultUtils.success();
    }
}
