package com.yodean.oa.module.noticle.controller;

import com.yodean.common.dto.Result;
import com.yodean.common.util.ResultUtils;
import com.yodean.oa.module.noticle.entity.Notice;
import com.yodean.oa.module.noticle.service.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by rick on 7/19/18.
 */
@RestController
@RequestMapping("/notices")
public class NoticeController {
    @Autowired
    private NoticeService noticeService;

    @PostMapping
    public Result<Long> save(@RequestBody Notice notice) {
        return ResultUtils.success(noticeService.save(notice).getId());
    }

    @PutMapping("/{id}")
    public Result update(@PathVariable Long id, @RequestBody Notice notice) {
        notice.setId(id);
        noticeService.save(notice);
        return ResultUtils.success();
    }

    @GetMapping("/{id}")
    public Result<Notice> findById(@PathVariable Long id) {
        return ResultUtils.success(noticeService.findById(id));
    }

    @DeleteMapping("/{id}")
    public Result deleteById(@PathVariable Long id) {
        noticeService.deleteById(id);
        return ResultUtils.success();
    }
}
