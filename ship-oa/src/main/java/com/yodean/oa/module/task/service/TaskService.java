package com.yodean.oa.module.task.service;

import com.yodean.common.enums.DelFlag;
import com.yodean.oa.common.core.service.BaseService;
import com.yodean.oa.module.task.entity.Discussion;
import com.yodean.oa.module.task.entity.Task;
import com.yodean.oa.module.task.entity.TaskLog;
import com.yodean.oa.module.task.repository.DiscussionRepository;
import com.yodean.oa.module.task.repository.TaskLogRepository;
import com.yodean.oa.module.task.repository.TaskRepository;
import com.yodean.platform.domain.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Objects;

/**
 * Created by rick on 7/18/18.
 */
@Service
public class TaskService extends BaseService<Task> {

    @Autowired
    private DiscussionRepository discussionRepository;

    @Autowired
    private TaskLogRepository taskLogRepository;

    @Autowired
    private TaskRepository taskRepository;

    @Override
    protected JpaRepository<Task, Long> autowired() {
        return taskRepository;
    }

    /**
     * 新建／编辑任务
     */
    @Transactional
    public Task save(Task task) {
        String log;

        if (Objects.isNull(task.getId())) {
            log = String.format("用户%s新建任务", UserUtils.getCurrentEmployee().getName());
        } else {
            log = String.format("用户%s编辑了任务", UserUtils.getCurrentEmployee().getName());
        }

        TaskLog taskLog = new TaskLog();
        taskLog.setTask(task);
        taskLog.setContent(log);

        task = super.save(task);

        taskLogRepository.save(taskLog);
        return task;
    }

    /**
     * 取消
     * @param id
     */
    @Transactional
    public void cancelById(Long id) {
        Task task = taskRepository.getOne(id);
        task.setDelFlag(DelFlag.DEL_FLAG_CLEAN);
        taskRepository.save(task);

        String log =  String.format("用户%s取消任务", UserUtils.getCurrentEmployee().getName());

        //记录日志
        TaskLog taskLog = new TaskLog();
        taskLog.setTask(task);
        taskLog.setContent(log);

        taskLogRepository.save(taskLog);

    }

    public Discussion addDiscussion(Discussion discussion, Long taskId) {
        discussion.initParams();

        Task task = new Task();
        task.setId(taskId);

        discussion.setTask(task);

        discussion = discussionRepository.save(discussion);
        return discussion;
    }

    public void deleteDiscussion(Long id) {
        Discussion discussion = discussionRepository.getOne(id);
        discussion.setDelFlag(DelFlag.DEL_FLAG_CLEAN);
        discussionRepository.save(discussion);
    }
}
