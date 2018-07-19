package com.yodean.oa.module.task.repository;

import com.yodean.oa.module.task.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by rick on 7/19/18.
 */
public interface TaskRepository extends JpaRepository<Task, Long> {
}
