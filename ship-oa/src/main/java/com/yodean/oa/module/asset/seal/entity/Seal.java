package com.yodean.oa.module.asset.seal.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.yodean.oa.module.asset.AssetStatus;
import com.yodean.platform.domain.BaseEntity;
import com.yodean.platform.domain.Employee;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Objects;

/**
 * Created by rick on 7/20/18.
 */
@Data
@Entity
@Table(name = "oa_seal")
@DynamicUpdate
public class Seal extends BaseEntity {
    /**
     * 印章名称
     */
    private String title;

    /**
     * 保管人
     */
    @Transient
    @JsonIgnoreProperties(allowSetters = true)
    private Long custodianId;

    @ManyToOne
    @JoinColumn(name = "custodian")
    private Employee custodian;

    /**
     * 关联审批
     */
    private Boolean associateFlow;

    /**
     * 印章状态
     */
    @Enumerated(EnumType.STRING)
    private AssetStatus assetStatus;

    @Override
    public void initParams() {
        if (Objects.nonNull(custodianId)) {
            custodian = new Employee();
            custodian.setId(custodianId);
        }
    }
}
