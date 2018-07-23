package com.yodean.oa.module.asset.seal.controller;

import com.yodean.common.dto.Result;
import com.yodean.common.util.ResultUtils;
import com.yodean.oa.common.core.controller.BaseController;
import com.yodean.oa.common.core.service.BaseService;
import com.yodean.oa.module.asset.AssetStatus;
import com.yodean.oa.module.asset.seal.entity.Seal;
import com.yodean.oa.module.asset.seal.service.SealService;
import com.yodean.oa.module.asset.seal.vo.SealVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by rick on 7/20/18.
 */
@RestController
@RequestMapping("/seals")
public class SealController extends BaseController<Seal> {

    @Autowired
    private SealService sealService;

    @Override
    protected BaseService autowired() {
        return sealService;
    }

    @GetMapping("/{id}")
    public Result<?> findById(@PathVariable Long id) {
        return ResultUtils.success(SealVO.format(sealService.findById(id)));
    }

    @PutMapping("/{id}")
    public Result update(@RequestBody Seal seal, @PathVariable Long id) {
        seal.setId(id);
        autowired().save(seal);
        return ResultUtils.success();
    }

    @PostMapping("/{id}/borrow")
    public Result borrow(@PathVariable Long id) {
        sealService.setSealStatus(id, AssetStatus.BORROWING);
        return ResultUtils.success();
    }

    @PostMapping("/{id}/back")
    public Result back(@PathVariable Long id) {
        sealService.setSealStatus(id, AssetStatus.NORMAL);
        return ResultUtils.success();
    }

}
