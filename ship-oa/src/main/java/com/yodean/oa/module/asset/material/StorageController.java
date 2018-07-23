package com.yodean.oa.module.asset.material;

import com.yodean.common.dto.Result;
import com.yodean.common.util.ResultUtils;
import com.yodean.oa.common.core.controller.BaseController;
import com.yodean.oa.common.core.service.BaseService;
import com.yodean.oa.module.asset.material.entity.Storage;
import com.yodean.oa.module.asset.material.service.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by rick on 7/20/18.
 */
@RestController
@RequestMapping("/storages")
public class StorageController extends BaseController<Storage>{

    @Autowired
    private StorageService storageService;

    @Override
    protected BaseService autowired() {
        return storageService;
    }

    @Override
    public Result update(@RequestBody Storage storage, @PathVariable Long id) {
        storage.setId(id);
        autowired().save(storage);
        return ResultUtils.success();
    }
}
