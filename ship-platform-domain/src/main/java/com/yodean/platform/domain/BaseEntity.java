package com.yodean.platform.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.yodean.common.enums.DelFlag;
import com.yodean.common.util.IDUtils;
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
        Date now = new Date();

        if (this.getId() == null) {
            this.setId(IDUtils.genItemId());
            this.setCreateBy(UserUtils.getCurrentEmployee().getUser().getId());
            this.setCreateDate(now);
        }

        if (Objects.isNull(this.getDelFlag()))
            this.setDelFlag(DelFlag.DEL_FLAG_NORMAL);

        this.setUpdateBy(UserUtils.getCurrentEmployee().getUser().getId());
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

    /**
     * 格式化参数
     */
    public void initParams(){}

    /**
     * 格式化输出
     */
    public void initResponse(){}

    @Override
    public int hashCode() {
        if (Objects.isNull(id))
            return super.hashCode();

        return id.hashCode();
    }
}
