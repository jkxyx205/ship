package com.yodean.platform.dict.controller;

import com.yodean.common.dto.Result;
import com.yodean.common.util.ResultUtils;
import com.yodean.dictionary.dto.DictDTO;
import com.yodean.dictionary.entity.Dict;
import com.yodean.dictionary.service.DictService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by rick on 7/18/18.
 */
@RestController
@RequestMapping("/dictionaries")
public class DictController {

    @Autowired
    private DictService dictService;

    /**
     * 添加字典
     * @param dictDTO
     * @return
     */
    @PostMapping
    public Result<Long> save(@RequestBody DictDTO dictDTO) {
        Dict dict = new Dict();
        BeanUtils.copyProperties(dictDTO, dict);
        dictService.save(dict);
        return ResultUtils.success(dict.getId());
    }


    @GetMapping("/{id}")
    public Result<Dict> findById(@PathVariable Long id) {
        return ResultUtils.success(dictService.findById(id));
    }
}
