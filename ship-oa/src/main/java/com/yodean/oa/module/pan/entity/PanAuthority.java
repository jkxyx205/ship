package com.yodean.oa.module.pan.entity;

import com.yodean.platform.domain.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import javax.persistence.*;

/**
 * All rights Reserved, Designed By www.xhope.top
 *
 * @version V1.0
 * @Description: 云盘权限表
 * @author: Rick.Xu
 * @date: 7/26/18 11:50
 * @Copyright: 2018 www.yodean.com. All rights reserved.
 */
@Getter
@Setter
@Entity
@Table(name = "oa_pan_authority",
        uniqueConstraints = {@UniqueConstraint(columnNames={"employee_id", "document_id", "permission_type", "inherit"})}
        )
public class PanAuthority extends BaseEntity {

    @Column(name = "document_id")
    private Long documentId;

    @Column(name = "employee_id")
    private Long employeeId;

    /**
     * 继承的权限
     */
    private Boolean inherit;

    @Column(name = "permission_type")
    @Enumerated(EnumType.STRING)
    private PermissionType permissionType;

    public static enum PermissionType {
        RENAME, UPLOAD, DELETE, READ, DOWNLOAD, CD, LIST, AUTHORITY
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof PanAuthority)) return false;

        PanAuthority that = (PanAuthority) o;

        return new EqualsBuilder()
                .append(getDocumentId(), that.getDocumentId())
                .append(getEmployeeId(), that.getEmployeeId())
                .append(getInherit(), that.getInherit())
                .append(getPermissionType(), that.getPermissionType())
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(getDocumentId())
                .append(getEmployeeId())
                .append(getInherit())
                .append(getPermissionType())
                .toHashCode();
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}