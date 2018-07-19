package com.yodean.platform.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Created by rick on 7/16/18.
 * 组织架构
 */
@Getter
@Setter
@Entity
@Table(name = "sys_department")
public class Department extends BaseEntity {

    /**
     * 部门名称
     */
    private String name;

    /**
     * 排序
     */
    private Integer sortId;

    /**
     * 父部门
     */
    @ManyToOne
    @JoinColumn(name = "parent_id")
    private Department parentDept;

    /**
     * 子部门
      */
    @OneToMany(mappedBy = "parentDept")
    private Set<Department> subDept;

    /**
     * 所属公司id
     */
    @ManyToOne
    @JoinColumn(name = "company_id")
    public Company company;

    /**
     * 部门成员
     */
    @ManyToMany(mappedBy = "departments")
    private Set<Employee> employees = new HashSet<>();

    /**
     * 路径path
     */
    private String path;

    @Transient
    private Long parentId;

    @Transient
    private Long companyId;

    @PreUpdate
    @PrePersist
    @PreRemove
    private void initData() {
        if (Objects.isNull(company) && Objects.nonNull(companyId)) {
            company = new Company();
            company.setId(companyId);
        }

        if (Objects.isNull(parentDept) && Objects.nonNull(parentId)) {
            parentDept = new Department();
            parentDept.setId(parentId);
        }
    }
}
