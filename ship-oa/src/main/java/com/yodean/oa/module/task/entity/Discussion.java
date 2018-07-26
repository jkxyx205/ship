package com.yodean.oa.module.task.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.yodean.oa.common.plugin.document.entity.Document;
import com.yodean.platform.domain.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.collections.CollectionUtils;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by rick on 7/19/18.
 */
@Setter
@Getter
@Entity
@Table(name = "oa_task_discussion")
public class Discussion extends BaseEntity {

    private String content;

    /***
     * 附件
     */
    @OneToMany
    @JoinColumn(name = "category_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "none",value = ConstraintMode.NO_CONSTRAINT))
    private List<Document> documents = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "task_id", nullable = false)
    @JsonIgnore
    private Task task;

    @Transient
    private Set<Long> docIds;

    @Override
    public void initParams() {
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

}
