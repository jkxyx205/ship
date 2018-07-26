package com.yodean.oa.module.meeting.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.yodean.oa.common.core.entity.ContentEntity;
import com.yodean.oa.module.inbox.ItemType;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * Created by rick on 7/18/18.
 */
@Getter
@Setter
@Entity
@Table(name = "oa_meeting")
public class Meeting extends ContentEntity {

    private enum MeetingType {
        MEETING, SCHEDULE;
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "meeting_type", nullable = false)
    private MeetingType meetingType;

    /***
     * 会议地址
     */
    private String address;

    /***
     * 可见度
     */
    private Boolean privacy;

    /**
     * 接收回执消息
     */
    private Boolean acceptMessage;

    @Transient
    @JsonIgnore
    protected ItemType itemType = ItemType.MEETING;
}
