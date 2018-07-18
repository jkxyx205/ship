package com.yodean.platform.domain;

import com.yodean.common.domain.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by rick on 7/13/18.
 * 公司信息
 */
@Getter
@Setter
@Entity
@Table(name = "sys_company")
public class Company extends BaseEntity {

    /**
     *  公司名称
     */
    private String name;

    /**
     *  行业类型
     */
    private String type;

    /**
     *  公司规模
     */
    private Integer employeeSize;

    /**
     *  公司所在地
     */
    private String city;

    /**
     *  详细地址
     */
    private String address;

    /**
     *  公司logo
     */
    private String logo;

    /**
     *  主管理员id
     */
    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name="admin_id")
    private Employee admin;

    /**
     * 所有部门
     */
    @OneToMany(mappedBy = "company", cascade = CascadeType.PERSIST)
    private Set<Department> departments = new HashSet<>();

}
