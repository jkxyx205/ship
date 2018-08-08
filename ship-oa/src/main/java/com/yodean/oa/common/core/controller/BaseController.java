package com.yodean.oa.common.core.controller;

import com.yodean.common.dto.Result;
import com.yodean.common.util.ResultUtils;
import com.yodean.oa.common.core.service.BaseService;
import com.yodean.platform.domain.BaseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by rick on 7/20/18.
 */
public abstract class BaseController<T extends BaseEntity, S extends BaseService> {

    @Autowired
    protected S service;

    /**
     * 添加对象
     * @param t
     * @return
     */
    @PostMapping
    public Result<Long> save(@RequestBody T t) {
        return ResultUtils.success(service.save(t).getId());
    }

    /**
     * 修改对象
     * @param t
     * @param id
     * @return
     */
    @PutMapping("/{id}")
    public Result<?> update(@RequestBody T t, @PathVariable Long id) {
        t.setId(id);
        service.save(t);
        return ResultUtils.success();
    }

    /**
     * 根据id查找对象
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public Result<?> findById(@PathVariable Long id) {
        return ResultUtils.success(service.findById(id));
    }

    /**
     * 删除对象
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Long id) {
        service.deleteByFlag(id);
        return ResultUtils.success();
    }
}
