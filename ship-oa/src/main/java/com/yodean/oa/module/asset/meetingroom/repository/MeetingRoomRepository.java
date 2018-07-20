package com.yodean.oa.module.asset.meetingroom.repository;

import com.yodean.oa.module.asset.meetingroom.entity.MeetingRoom;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by rick on 7/20/18.
 */
public interface MeetingRoomRepository extends JpaRepository<MeetingRoom, Long> {
}
