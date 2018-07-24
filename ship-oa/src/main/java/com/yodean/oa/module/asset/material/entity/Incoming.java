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
 * 入库动作
 */
@Getter
@Setter
@Entity
@Table(name = "oa_material_incoming")
public class Incoming extends BaseEntity {
    /**
     * 物料
     */
    @ManyToOne
    @JoinColumn(name = "material_id")
    private Material material;

    /**
     * 入库数量
     */
    private Double num;

    /**
     * 入库单位
     */
    @ManyToOne
    @JoinColumn(name = "incoming_unit_id")
    private MaterialUnit materialUnit;


    /**
     * 换算基本数量
     */
    private Double baseNum;

    /**
     * 库位
     */
    @ManyToOne
    @JoinColumn(name = "storage_id")
    private Storage storage;

    //Transient
    @Transient
    @JsonIgnoreProperties
    private Set<MaterialSerialNumber> materialSerialNumberList = new HashSet<>();

    @Transient
    @JsonIgnoreProperties
    private Long materialId;

    @Transient
    @JsonIgnoreProperties
    private Long materialUnitId;


    @Transient
    @JsonIgnoreProperties
    private Long storageId;

    @Transient
    @JsonIgnoreProperties
    private Set<String> materialSerialNumberIds = new HashSet<>();

    @Override
    public void initParams() {
        material = new Material();
        material.setId(materialId);

        materialUnit = new MaterialUnit();
        materialUnit.setId(materialUnitId);

        storage = new Storage();
        storage.setId(storageId);

        materialSerialNumberIds.forEach(s -> {
            MaterialSerialNumber materialSerialNumber = new MaterialSerialNumber();
            materialSerialNumber.setNumber(s);
            materialSerialNumber.setMaterial(material);
            materialSerialNumber.setStorage(storage);
            materialSerialNumberList.add(materialSerialNumber);
        });
    }
}
