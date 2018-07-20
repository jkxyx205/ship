package com.yodean.oa.module.asset.vehicle.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.yodean.dictionary.DictJpaConverter;
import com.yodean.dictionary.entity.Dict;
import com.yodean.oa.module.asset.AssetStatus;
import com.yodean.platform.domain.BaseEntity;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Date;

/**
 * Created by rick on 7/20/18.
 */
@Data
@Entity
@Table(name = "oa_vehicle")
public class Vehicle extends BaseEntity {
    /**
     * 车牌号
     */
    @NotBlank
    private String licence;

    /**
     * 车辆类型
     */
    @Convert(converter = DictJpaConverter.class)
    private Dict vehicleType;

    /**
     * 品牌型号（从字典中获取）
     */
    @Convert(converter = DictJpaConverter.class)
    private Dict vehicleBrand;

    /**
     * 车辆识别代号
     */
    private String identifyCode;

    /**
     * 发动机号码
     */
    private String engineCode;

    /**
     * 购买日期
     */
    @Temporal(TemporalType.DATE)
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd") //覆盖全局的 yyyy-MM-dd HH:mm:ss
    private Date buyDate;

    /**
     * 载客数
     */
    private Integer capacity;

    /**
     * 保管人
     */
    private Integer custodian;

    /**
     * 车辆状体
     */
    @Enumerated(EnumType.STRING)
    private AssetStatus assetStatus;

    /**
     * 是否关联审批
     */
    private Boolean associateFlow;
}
