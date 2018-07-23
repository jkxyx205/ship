package com.yodean.oa.module.asset.material.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.yodean.platform.domain.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by rick on 7/23/18.
 */
@Getter
@Setter
@Entity
@Table(name = "oa_material_unit_category")
public class UnitCategory extends BaseEntity {

    /**
     * 标题
     */
    private String title;

    /**
     * 基本单位
     */
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "base_unit_id", updatable = false)
    private MaterialUnit baseUnit;

    /**
     * 分类所有单位
     */
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "unitCategory", orphanRemoval = true)
    private List<MaterialUnit> materialUnitList = new ArrayList<>();

    /**
     * null 系统维度
     * ～ 物料号id
     */
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "type", foreignKey = @ForeignKey(name = "none", value = ConstraintMode.NO_CONSTRAINT))
    @JsonIgnore
    private Material material;

    @Override
    public void initParams() {
        if (Objects.nonNull(baseUnit)) {
            baseUnit.setUnitCategory(this);
        }
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) return true;
        if (that == null || getClass() != that.getClass()) return false;
        if (that instanceof UnitCategory) {
            UnitCategory unitCategory = (UnitCategory) that;

            if (Objects.nonNull(this.getMaterial()) &&
                    Objects.nonNull(unitCategory.getMaterial()) &&
                    Objects.equals(this.getMaterial().getId(), unitCategory.getMaterial().getId())) {

                return true;

            } else{
                return super.equals(that);
            }
        }

        return false;
    }

    @Override
    public int hashCode() {
        if (Objects.nonNull(this.material))
            return Objects.hash(this.material.getId());
        else
            return super.hashCode();
    }
}
