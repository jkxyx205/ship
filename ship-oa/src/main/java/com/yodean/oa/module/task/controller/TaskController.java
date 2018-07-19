package com.yodean.oa.module.task.controller;

import com.yodean.common.dto.Result;
import com.yodean.common.util.ResultUtils;
import com.yodean.oa.module.task.entity.Discussion;
import com.yodean.oa.module.task.entity.Task;
import com.yodean.oa.module.task.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by rick on 7/19/18.
 */
@RestController
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @PostMapping
    public Result<Long> save(@RequestBody Task task) {
        return ResultUtils.success(taskService.save(task).getId());
    }

    @PutMapping("/{id}")
    public Result update(@RequestBody Task task, @PathVariable Long id) {
        task.setId(id);
        taskService.save(task);
        return ResultUtils.success();
    }

    @GetMapping("/{id}")
    public Result<Task> findById(@PathVariable Long id) {
        return ResultUtils.success(taskService.findById(id));
    }


    @DeleteMapping("/{id}")
    public Result cancelById(@PathVariable Long id) {
        taskService.cancelById(id);
        return ResultUtils.success();
    }

    /**
     * 添加评论
     * @param discussion
     * @return
     */
    @PostMapping("/{taskId}/discussions")
    public Result<Long> addDiscussion(@RequestBody Discussion discussion, @PathVariable Long taskId) {
        return ResultUtils.success(taskService.addDiscussion(discussion, taskId).getId());
    }

    /**
     * 删除评论
     * @param id
     * @return
     */
    @DeleteMapping("/discussions/{id}")
    public Result addDiscussion(@PathVariable Long id) {
        taskService.deleteDiscussion(id);
        return ResultUtils.success();
    }

}
