package com.yodean.oa.module.asset.material.entity;

import com.yodean.platform.domain.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Created by rick on 7/24/18.
 */
@Getter
@Setter
@Entity
@Table(name = "oa_material_serial_number")
public class MaterialSerialNumber extends BaseEntity{

    /**
     * 序列号
     */
    private String number;

    @ManyToOne
    @JoinColumn(name = "material_id")
    private Material material;

    /**
     * 仓库
     */
    @ManyToOne
    @JoinColumn(name = "storage_id")
    private Storage storage;

}
