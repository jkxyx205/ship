package com.yodean.oa.module.meeting.service;

import com.rick.db.util.EntityBeanUtils;
import com.yodean.common.util.JacksonUtils;
import com.yodean.oa.common.plugin.document.enums.DocumentCategory;
import com.yodean.oa.common.plugin.document.service.DocumentService;
import com.yodean.oa.module.meeting.entity.Meeting;
import com.yodean.oa.module.meeting.repository.MeetingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;

/**
 * Created by rick on 7/18/18.
 */
@Service
public class MeetingService {
    @Autowired
    private MeetingRepository meetingRepository;

    @Autowired
    private DocumentService documentService;

    @Transactional
    public Meeting save(Meeting meeting) {
        //参数格式化
        meeting.initData();

        if (Objects.nonNull(meeting.getId())) { //修改
            Meeting persist = meetingRepository.findById(meeting.getId()).get();

            EntityBeanUtils.merge(persist, meeting, true);
            meeting = persist;
        }

        meetingRepository.save(meeting);

//        documentService.update(meeting.getDocIds(), DocumentCategory.MEETING, meeting.getId());
        return meeting;
    }

    public Meeting findById(Long id) {
        Meeting meeting = meetingRepository.getOne(id);
        meeting.setLabelList(JacksonUtils.readValue(meeting.getLabels(), List.class));

        return meeting;
    }

}