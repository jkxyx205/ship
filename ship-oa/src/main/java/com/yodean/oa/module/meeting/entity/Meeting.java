package com.yodean.oa.module.meeting.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.yodean.common.domain.BaseEntity;
import com.yodean.common.util.JacksonUtils;
import com.yodean.oa.common.plugin.document.entity.Document;
import com.yodean.oa.common.plugin.document.enums.DocumentCategory;
import com.yodean.oa.module.inbox.ItemStatus;
import com.yodean.oa.module.inbox.ItemType;
import com.yodean.oa.module.inbox.Priority;
import com.yodean.oa.module.inbox.entity.UserInbox;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.Where;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.*;

/**
 * Created by rick on 7/18/18.
 */
@Getter
@Setter
@Entity
@Table(name = "oa_meeting")
public class Meeting extends BaseEntity {

    private enum MeetingType {
        MEETING, SCHEDULE;
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "meeting_type", nullable = false)
    private MeetingType meetingType;

    /***
     * 会议标题
     */
    @NotBlank(message = "名称不能为空")
    @Column(nullable = false)
    private String title;

    @Length(max = 10000, message = "正文内容不能超过10000个字符")
    @NotBlank(message = "会议正文不能为空")
    @Column(columnDefinition = "text", nullable = false)
    private String content;

    /***
     * 会议地址
     */
    private String address;

    /***
     * 可见度
     */
    private Boolean privacy;

    /***
     * 会议开始时间
     */
    @Column(name="start_date")
    private Date startDate;

    /***
     * 会议结束时间
     */
    @Column(name="end_date")
    private Date endDate;

    /***
     * 提醒开始时间
     */
    @Column(name="tip_start_date")
    private Date tipStartDate;

    /***
     * 提醒结束时间
     */
    @Column(name="tip_end_date")
    private Date tipEndDate;

    /***
     * 优先级
     */
    @Enumerated(EnumType.STRING)
    @Column
    private Priority priority;

    /***
     * 标签
     */
    @JsonIgnore
    private String labels;


    @Transient
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Set<Long> docIds = new HashSet<>();

    @Transient
    private List<String> labelList = new ArrayList<>();

    /***
     * 附件
     */
    @OneToMany
    @JoinColumn(name = "category_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "none",value = ConstraintMode.NO_CONSTRAINT))
    @Where(clause = "category = 'MEETING'")
    private List<Document> documents = new ArrayList<>();

    /**
     * 授权对象
     */
    @JoinColumn(name = "item_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "none",value = ConstraintMode.NO_CONSTRAINT))
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @Where(clause = "item_type = 'MEETING'")
    private List<UserInbox> userList = new ArrayList<>();

    public void initData() {
        //标签
        if (StringUtils.isBlank(this.getLabels()) && CollectionUtils.isNotEmpty(labelList)) {
            setLabels(JacksonUtils.toJSon(labelList));
        }


        //初始化inbox
        userList.forEach(userInbox -> {
            userInbox.setItemType(ItemType.MEETING);
            userInbox.setItemId(this.getId());
            userInbox.setItemStatus(ItemStatus.INBOX);
            userInbox.setAuthorityType(UserInbox.AuthorityType.USER);
        });

        //附件document
        if (CollectionUtils.isEmpty(documents) && CollectionUtils.isNotEmpty(docIds)) {
            for (Long docId : docIds) {
                Document document = new Document();
                document.setId(docId);
                document.setCategoryId(this.getId());
                document.setCategory(DocumentCategory.MEETING);
                getDocuments().add(document);
            }
        }
    }
}
