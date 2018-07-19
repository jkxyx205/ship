package com.yodean.oa.module.task.repository;

import com.yodean.oa.module.task.entity.Discussion;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by rick on 7/19/18.
 */
public interface DiscussionRepository extends JpaRepository<Discussion, Long> {
}
