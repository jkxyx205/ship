package com.yodean.oa.module.task.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.yodean.platform.domain.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Created by rick on 7/19/18.
 */
@Getter
@Setter
@Entity
@Table(name = "oa_task_log")
public class TaskLog extends BaseEntity {

    private String content;

    @ManyToOne
    @JoinColumn(name = "task_id", referencedColumnName = "id", nullable = false)
    @JsonIgnore
    private Task task;

}
