package com.yodean.oa.module.inbox.repository;

import com.yodean.oa.module.inbox.entity.UserInbox;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by rick on 7/19/18.
 */
public interface UserInboxRepository extends JpaRepository<UserInbox, Long> {
}
