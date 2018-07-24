package com.yodean.oa.module.asset.material.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.yodean.platform.domain.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by rick on 7/24/18.
 */
@Getter
@Setter
@Entity
@Table(name = "oa_material_moving")
public class Moving extends BaseEntity {
    /**
     * 物料
     */
    @ManyToOne
    @JoinColumn(name = "material_id")
    private Material material;

    /**
     * 移库数量
     */
    private Double num;

    /**
     * 移库单位
     */
    @ManyToOne
    @JoinColumn(name = "incoming_unit_id")
    private MaterialUnit materialUnit;

    /**
     * 换算基本数量
     */
    private Double baseNum;

    /**
     * 移出库位
     */
    @ManyToOne
    @JoinColumn(name = "src_storage_id")
    private Storage srcStorage;

    /**
     * 目标库位
     */
    @ManyToOne
    @JoinColumn(name = "dist_storage_id")
    private Storage distStorage;

    @Transient
    @JsonIgnoreProperties
    private Long materialId;

    @Transient
    @JsonIgnoreProperties
    private Long materialUnitId;

    @Transient
    @JsonIgnoreProperties
    private Long srcStorageId;


    @Transient
    @JsonIgnoreProperties
    private Long distStorageId;

    @Transient
    @JsonIgnoreProperties
    private Set<String> materialSerialNumberIds = new HashSet<>();

    @Override
    public void initParams() {
        material = new Material();
        material.setId(materialId);

        materialUnit = new MaterialUnit();
        materialUnit.setId(materialUnitId);

        srcStorage = new Storage();
        srcStorage.setId(srcStorageId);

        distStorage = new Storage();
        distStorage.setId(distStorageId);

    }
}
