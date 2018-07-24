package com.yodean.oa.module.asset.material.controller;

import com.yodean.oa.common.core.controller.BaseController;
import com.yodean.oa.common.core.service.BaseService;
import com.yodean.oa.module.asset.material.entity.Incoming;
import com.yodean.oa.module.asset.material.service.IncomingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by rick on 7/24/18.
 */
@RestController
@RequestMapping("/materials/incoming")
public class IncomingController extends BaseController<Incoming> {

    @Autowired
    private IncomingService incomingService;


    @Override
    protected BaseService autowired() {
        return incomingService;
    }
}
