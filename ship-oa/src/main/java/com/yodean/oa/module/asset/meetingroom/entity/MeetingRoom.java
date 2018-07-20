package com.yodean.oa.module.asset.meetingroom.entity;

import com.yodean.platform.domain.BaseEntity;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by rick on 7/20/18.
 */
@Data
@Entity
@Table(name = "oa_meeting_room")
public class MeetingRoom extends BaseEntity {

    /**
     * 会议室名称
     */
    @Column(nullable = false)
    private String title;

    /**
     * 会议室容积
     */
    private Integer capacity;

    /**
     * 会议室器材
     */
    private String equipment;

    /**
     * 会议室地址
     */
    @Column(nullable = false)
    private String address;
}
