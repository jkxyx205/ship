package com.yodean.oa.module.task.repository;

import com.yodean.oa.module.task.entity.TaskLog;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by rick on 7/19/18.
 */
public interface TaskLogRepository extends JpaRepository<TaskLog, Long> {
}
