package com.yodean.oa.module.asset.material.entity;

import com.yodean.platform.domain.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by rick on 7/23/18.
 */
@Getter
@Setter
@Entity
@Table(name = "oa_material")
public class Material extends BaseEntity {

    /**
     * 物料id
     */
    private String bid;

    /**
     * 物料名称
     */
    private String title;


    /**
     * 物料规格
     */
    private String specification;

    /**
     * 物料类型
     * 0 设备
     * 1 耗材
     */
    private MaterialType materialType;

    /**
     * 基本单位
     */
    @ManyToOne
    @JoinColumn(name = "base_unit_id")
    private MaterialUnit baseUnit;

    /**
     * 换算维度
     */
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "material")
    private UnitCategory unitCategory = new UnitCategory();

    @Transient
    private Long baseUnitId;

    @Transient
    private List<MaterialUnit> materialUnitList = new ArrayList<>();
    /**
     * S. 设备
     * H. 耗材
     */
    @Getter
    public static enum  MaterialType {
        S("设备"), H("耗材");
        private String description;

        MaterialType(String description) {
            this.description = description;
        }

    }

    @Override
    public void initParams() {

        unitCategory.setTitle(title);
        unitCategory.setMaterial(this);
        unitCategory.getMaterialUnitList().addAll(materialUnitList);

        for (MaterialUnit materialUnit : materialUnitList) {
            materialUnit.setUnitCategory(unitCategory);
        }

        unitCategory.getBaseUnit().setUnitCategory(unitCategory);

    }
}
