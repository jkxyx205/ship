package com.yodean.oa.common.core.controller;

import com.yodean.common.dto.Result;
import com.yodean.common.util.ResultUtils;
import com.yodean.oa.common.core.service.BaseService;
import com.yodean.platform.domain.BaseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Created by rick on 7/20/18.
 */
public abstract class BaseController<T extends BaseEntity> {

    abstract protected BaseService autowired();

    @PostMapping
    public Result<Long> save(@RequestBody T t) {
        return ResultUtils.success(autowired().save(t).getId());
    }

    @PutMapping("/{id}")
    public Result update(@RequestBody T t, @PathVariable Long id) {
        t.setId(id);
        autowired().save(t);
        return ResultUtils.success();
    }

    @GetMapping("/{id}")
    public Result<?> findById(@PathVariable Long id) {
        return ResultUtils.success(autowired().findById(id));
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Long id) {
        autowired().deleteByFlag(id);
        return ResultUtils.success();
    }

}
