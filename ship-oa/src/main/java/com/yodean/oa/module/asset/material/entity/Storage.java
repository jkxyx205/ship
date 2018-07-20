package com.yodean.oa.module.asset.material.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.yodean.oa.module.asset.AssetStatus;
import com.yodean.platform.domain.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by rick on 7/20/18.
 */
@Getter
@Setter
@Entity
@Table(name = "oa_material_storage")
public class Storage extends BaseEntity {
    /**
     * 库位Id
     */
    private String sid;

    /**
     * 库位名称
     */
    private String title;

    /**
     * 仓库状态
     */
    @Enumerated(EnumType.STRING)
    private AssetStatus assetStatus;

    /**
     * 父库存
     */
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "parent_id")
    private Storage parentStorage;

    /**
     * 接收参数
     */
    @Transient
    @JsonIgnoreProperties(allowSetters = true)
    private Long parentId;

    /**
     * 子库存
     */
    @OneToMany(mappedBy = "parentStorage")
    private List<Storage> storageList = new ArrayList<>();

    @Override
    public void initParams() {
        if (Objects.nonNull(parentId)) {
            parentStorage = new Storage();
            parentStorage.setId(parentId);
        }
    }
}
