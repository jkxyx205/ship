package com.yodean.oa.common.core.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.yodean.common.util.JacksonUtils;
import com.yodean.oa.common.plugin.document.entity.Document;
import com.yodean.oa.module.inbox.ItemStatus;
import com.yodean.oa.module.inbox.ItemType;
import com.yodean.oa.module.inbox.Priority;
import com.yodean.oa.module.inbox.entity.UserInbox;
import com.yodean.platform.domain.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.*;

/**
 * Created by rick on 7/19/18.
 */
@MappedSuperclass
@Getter
@Setter
public class ContentEntity extends BaseEntity {

    /***
     * 标题
     */
    @NotBlank(message = "标题不能为空")
    @Column(name = "title", nullable = false)
    private String title;

    @Length(max = 10000, message = "正文内容不能超过10000个字符")
    @NotBlank(message = "正文内容不能为空")
    @Column(columnDefinition = "text", nullable = false)
    private String content;

    /***
     * 开始时间
     */
    @Column(name="start_date")
    private Date startDate;

    /***
     * 结束时间
     */
    @Column(name="end_date")
    private Date endDate;

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
    private List<String> labelList = new ArrayList<>();

    /***
     * 附件
     */
    @OneToMany
    @JoinColumn(name = "category_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "none",value = ConstraintMode.NO_CONSTRAINT))
    private List<Document> documents = new ArrayList<>();

    @Transient
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Set<Long> docIds = new HashSet<>();

    /**
     * 授权对象
     */
    @JoinColumn(name = "item_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "none",value = ConstraintMode.NO_CONSTRAINT))
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserInbox> userList = new ArrayList<>();

    @Transient
    protected ItemType itemType;


    @Override
    public void initParams() {
        //标签
        if (StringUtils.isBlank(this.getLabels()) && CollectionUtils.isNotEmpty(labelList)) {
            setLabels(JacksonUtils.toJSon(labelList));
        }

        //初始化inbox
        userList.forEach(userInbox -> {
            userInbox.setItemType(this.getItemType());
            userInbox.setItemId(this.getId());
            userInbox.setItemStatus(ItemStatus.INBOX);

            if (Objects.isNull(userInbox.getAuthorityType()))
                userInbox.setAuthorityType(UserInbox.AuthorityType.USER);
        });

        //附件document
        if (CollectionUtils.isEmpty(documents) && CollectionUtils.isNotEmpty(docIds)) {
            for (Long docId : docIds) {
                Document document = new Document();
                document.setId(docId);
                document.setCategoryId(this.getId());
                getDocuments().add(document);
            }
        }
    }

    @Override
    public void initResponse() {
        this.setLabelList(JacksonUtils.readValue(this.getLabels(), List.class));
    }
}
