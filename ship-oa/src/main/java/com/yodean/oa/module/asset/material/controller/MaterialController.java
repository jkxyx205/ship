package com.yodean.oa.module.asset.material.controller;

import com.yodean.oa.common.core.controller.BaseController;
import com.yodean.oa.common.core.service.BaseService;
import com.yodean.oa.module.asset.material.entity.Material;
import com.yodean.oa.module.asset.material.service.MaterialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by rick on 7/23/18.
 */
@RestController
@RequestMapping("/materials")
public class MaterialController extends BaseController<Material> {

    @Autowired
    private MaterialService materialService;


    @Override
    protected BaseService autowired() {
        return materialService;
    }

}
