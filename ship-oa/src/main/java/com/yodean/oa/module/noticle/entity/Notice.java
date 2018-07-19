package com.yodean.oa.module.noticle.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.yodean.oa.common.core.entity.ContentEntity;
import com.yodean.oa.common.plugin.document.entity.Document;
import com.yodean.oa.common.plugin.document.enums.DocumentCategory;
import com.yodean.oa.module.inbox.ItemType;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * Created by rick on 7/19/18.
 */
@Getter
@Setter
@Entity
@Table(name="oa_notice")
public class Notice extends ContentEntity {

    @Transient
    @JsonIgnore
    protected ItemType itemType = ItemType.NOTICE;

    @Transient
    @JsonIgnore
    protected DocumentCategory documentCategory = DocumentCategory.NOTICE;

    /**
     * 置顶
     */
    private Boolean top;

    /**
     * 封面
     */
    @OneToOne
    @JoinColumn(name = "cover_id")
    private Document cover;

    @Transient
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Long coverId;

    @Enumerated(EnumType.STRING)
    private NoticeType noticeType;

    public static enum NoticeType {
        NOTICE, NEWS;
    }

}
