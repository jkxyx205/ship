package com.yodean.oa.module.note.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.yodean.oa.common.core.entity.ContentEntity;
import com.yodean.oa.common.plugin.document.enums.DocumentCategory;
import com.yodean.oa.module.inbox.ItemType;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * Created by rick on 7/18/18.
 */
@Getter
@Setter
@Entity
@Table(name="oa_note")
public class Note extends ContentEntity {

    @Transient
    @JsonIgnore
    protected ItemType itemType = ItemType.NOTE;

    @Transient
    @JsonIgnore
    protected DocumentCategory documentCategory = DocumentCategory.NOTE;

}
