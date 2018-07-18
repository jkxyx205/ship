package com.yodean.common.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.yodean.common.enums.DelFlag;
import com.yodean.common.util.IDUtils;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.EqualsBuilder;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 * Created by rick on 7/13/18.
 */
@MappedSuperclass
@Getter
@Setter
@JsonIgnoreProperties(value = {"handler","hibernateLazyInitializer","fieldHandler", "delFlag"})
public class BaseEntity implements Serializable {

    @Id
    private Long id;

    @Column(name = "create_by", updatable = false)
    private Long createBy;

    @Column(name = "create_date", nullable = false, updatable = false)
    private Date createDate;

    @Column(name = "update_by", nullable = false)
    private Long updateBy;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "update_date", nullable = false)
    private Date updateDate;

    private String remarks;


    @JsonIgnore
    @Enumerated
    @Column(name = "del_flag", nullable = false)
    private DelFlag delFlag;

    @PrePersist
    @PreUpdate
    private void preInsert() {
        Long userId = 102L;

        Date now = new Date();

        if (this.getId() == null) {
            this.setId(IDUtils.genItemId());
            this.setCreateBy(userId);
            this.setCreateDate(now);

        }

        if (Objects.isNull(this.getDelFlag()))
            this.setDelFlag(DelFlag.DEL_FLAG_NORMAL);

        this.setUpdateBy(userId);
        this.setUpdateDate(now);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        if (obj instanceof BaseEntity) {
            BaseEntity dataEntity = (BaseEntity) obj;
            if (dataEntity.id != null && new EqualsBuilder().append(dataEntity.id, id).isEquals())
                return true;
        }

        return false;
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
