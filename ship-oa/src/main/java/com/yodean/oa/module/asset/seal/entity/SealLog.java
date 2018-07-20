package com.yodean.oa.module.asset.seal.entity;

import com.yodean.platform.domain.BaseEntity;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Created by rick on 7/20/18.
 */
@Data
@Entity
@Table(name = "oa_seal_log")
public class SealLog extends BaseEntity {

    private String content;

    @ManyToOne
    @JoinColumn(name = "seal_id")
    private Seal seal;
}
