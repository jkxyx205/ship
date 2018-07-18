package com.yodean.oa.module.meeting.repository;

import com.yodean.oa.module.meeting.entity.Meeting;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by rick on 7/18/18.
 */
public interface MeetingRepository extends JpaRepository<Meeting, Long> {
}
