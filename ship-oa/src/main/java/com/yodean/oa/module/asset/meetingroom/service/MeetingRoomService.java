package com.yodean.oa.module.asset.meetingroom.service;

import com.yodean.oa.common.core.service.BaseService;
import com.yodean.oa.module.asset.meetingroom.entity.MeetingRoom;
import com.yodean.oa.module.asset.meetingroom.repository.MeetingRoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

/**
 * Created by rick on 7/20/18.
 */
@Service
public class MeetingRoomService extends BaseService<MeetingRoom> {
    @Autowired
    private MeetingRoomRepository meetingRoomRepository;

    @Override
    protected JpaRepository autowired() {
        return meetingRoomRepository;
    }
}
