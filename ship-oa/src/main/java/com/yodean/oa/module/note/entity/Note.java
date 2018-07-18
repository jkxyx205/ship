package com.yodean.oa.module.note.entity;

import com.yodean.common.domain.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

/**
 * Created by rick on 7/18/18.
 */
@Getter
@Setter
@Entity
@Table(name="oa_note")
public class Note extends BaseEntity {



    private String title;

}
