package com.yodean.oa.module.asset.material.entity;

import com.yodean.oa.module.asset.material.MaterialUnitUtils;
import com.yodean.platform.domain.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.EqualsBuilder;

import javax.persistence.*;
import java.util.Objects;

/**
 * Created by rick on 7/23/18.
 */
@Getter
@Setter
@Entity
@Table(name = "oa_material_unit")
public class MaterialUnit extends BaseEntity {
    /**
     * 单位英文标示，如m，kg
     */
    private String name;

    /**
     * 单位中文名称，如米，千克
     */
    private String title;
    /**
     * 分母
     */
    private Integer denominator;

    /**
     * 分子
     */
    private Integer numerator;

    /**
     * 常量
     */
    private Double constant;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private UnitCategory unitCategory;

    @Transient
    private Long categoryId;

    @Override
    public void initParams() {
        if (Objects.nonNull(categoryId)) {
            unitCategory = new UnitCategory();
            unitCategory.setId(categoryId);
        }
    }

    /**
     * 转换基本单位
     * @return
     */
    public Double convertToBaseUnit() {
        return MaterialUnitUtils.calculate(this, unitCategory.getBaseUnit());
    }


    /**
     * 获取转换信息
     *
     * @return
     */
    public String getConversionText() {
        StringBuilder sb = new StringBuilder(1 + " ");
        sb.append(this.getName()).append(" = ");
        sb.append(convertToBaseUnit());
        sb.append(" " + this.getUnitCategory().getBaseUnit().getName());

        return sb.toString();
    }

    /**
     * 获取完整公式
     *
     * @return
     */
    public String getConversionFullText() {
        StringBuilder sb = new StringBuilder("A * " + this.title + " = (A * " + this.numerator + "/" + this.denominator + "  + " + this.constant + ") * " + this.getUnitCategory().getBaseUnit().title + "");
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MaterialUnit)) return false;
        if (super.equals(o)) return true;

        MaterialUnit that = (MaterialUnit) o;

        return new EqualsBuilder()
                .append(this.getUnitCategory().getId(), that.getUnitCategory().getId())
                .append(this.name, that.name)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getUnitCategory().getId(), this.name);
    }


}
