package com.yodean.oa.module.task.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.yodean.oa.common.core.entity.ContentEntity;
import com.yodean.oa.module.inbox.ItemType;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Where;
import org.hibernate.validator.constraints.Range;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.List;

/**
 * Created by rick on 7/19/18.
 */
@Getter
@Setter
@Entity
@Table(name = "oa_task")
public class Task extends ContentEntity {

    @Transient
    @JsonIgnore
    protected ItemType itemType = ItemType.TASK;

    /***
     * 进度
     */
    @Range(min = 0, max = 100)
    private Integer progress;

    /**
     * 讨论
     */
    @OneToMany(mappedBy = "task")
    @Where(clause = "del_flag = '1'")
    private List<Discussion> discussions;

    /**
     * 日志
     */
    @OneToMany(mappedBy = "task")
    private List<TaskLog> taskLogs;

}
