package com.yodean.oa.module.asset.material.controller;

import com.yodean.common.dto.Result;
import com.yodean.common.util.ResultUtils;
import com.yodean.oa.common.core.controller.BaseController;
import com.yodean.oa.module.asset.material.entity.MaterialUnit;
import com.yodean.oa.module.asset.material.entity.UnitCategory;
import com.yodean.oa.module.asset.material.service.MaterialUnitService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by rick on 7/23/18.
 */
@RestController
@RequestMapping("/units")
public class MaterialUnitController extends BaseController<MaterialUnit, MaterialUnitService> {

    @RequestMapping("/categories")
    public Result<Long> addUnitCategory(@RequestBody UnitCategory unitCategory) {
       return ResultUtils.success(service.addUnitCategory(unitCategory).getId());
    }

}
