package com.yodean.oa.module.asset.seal.controller;

import com.yodean.common.dto.Result;
import com.yodean.common.util.ResultUtils;
import com.yodean.oa.common.core.controller.BaseController;
import com.yodean.oa.module.asset.AssetStatus;
import com.yodean.oa.module.asset.seal.entity.Seal;
import com.yodean.oa.module.asset.seal.service.SealService;
import com.yodean.oa.module.asset.seal.vo.SealVO;
import org.springframework.web.bind.annotation.*;

/**
 * Created by rick on 7/20/18.
 */
@RestController
@RequestMapping("/seals")
public class SealController extends BaseController<Seal, SealService> {


    @GetMapping("/{id}")
    public Result<?> findById(@PathVariable Long id) {
        return ResultUtils.success(SealVO.format(service.findById(id)));
    }

    @PostMapping("/{id}/borrow")
    public Result borrow(@PathVariable Long id) {
        service.setSealStatus(id, AssetStatus.BORROWING);
        return ResultUtils.success();
    }

    @PostMapping("/{id}/back")
    public Result back(@PathVariable Long id) {
        service.setSealStatus(id, AssetStatus.NORMAL);
        return ResultUtils.success();
    }
}
