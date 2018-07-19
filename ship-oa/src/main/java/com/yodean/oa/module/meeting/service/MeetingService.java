package com.yodean.oa.module.meeting.service;

import com.yodean.common.enums.DelFlag;
import com.yodean.oa.common.core.service.BaseService;
import com.yodean.oa.module.inbox.ItemType;
import com.yodean.oa.module.inbox.service.UserInboxService;
import com.yodean.oa.module.meeting.entity.Meeting;
import com.yodean.oa.module.meeting.repository.MeetingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * Created by rick on 7/18/18.
 */
@Service
public class MeetingService extends BaseService<Meeting> {
    @Autowired
    private MeetingRepository meetingRepository;

    @Autowired
    private UserInboxService userInboxService;


    @Override
    protected JpaRepository<Meeting, Long> autowired() {
        return meetingRepository;
    }

    @Transactional
    public Meeting save(Meeting meeting) {
        super.saveCascade(meeting);
        userInboxService.tipAll(ItemType.MEETING, meeting.getId());
        return meeting;
    }

    /**
     * 取消
     * @param id
     */
    public void cancelById(Long id) {
        Meeting task = meetingRepository.getOne(id);
        task.setDelFlag(DelFlag.DEL_FLAG_REMOVE);
        meetingRepository.save(task);
    }

}