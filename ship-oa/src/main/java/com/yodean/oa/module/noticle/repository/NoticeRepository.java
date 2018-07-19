package com.yodean.oa.module.noticle.repository;

import com.yodean.oa.module.noticle.entity.Notice;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by rick on 7/19/18.
 */
public interface NoticeRepository extends JpaRepository<Notice, Long> {
}
