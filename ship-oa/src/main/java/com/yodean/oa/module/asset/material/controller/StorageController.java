package com.yodean.oa.module.asset.material.controller;

import com.yodean.oa.common.core.controller.BaseController;
import com.yodean.oa.module.asset.material.entity.Storage;
import com.yodean.oa.module.asset.material.service.StorageService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by rick on 7/20/18.
 */
@RestController
@RequestMapping("/storages")
public class StorageController extends BaseController<Storage, StorageService> {

}
