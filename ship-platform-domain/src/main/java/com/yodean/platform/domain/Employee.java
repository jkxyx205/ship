package com.yodean.platform.domain;

import com.yodean.common.domain.EntityData;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by rick on 7/16/18.
 * 公司员工
 */
@Getter
@Setter
@Entity
@Table(name = "sys_employee")
public class Employee extends EntityData {
    /**
     * 姓名
     */
    private String name;

    /**
     * 电话
     */
    private String tel;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 工号
     */
    private String employeeId;

    /**
     * 职位
     */
    private String position;

    /**
     * 部门管理者
     */
    private Boolean leader;

    /**
     * 用户Id
    */
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    /**
     * 所属部门
     */
    @ManyToMany
    @JoinTable(
            name = "sys_dept_emp",
            joinColumns = { @JoinColumn(name = "employee_id") },
            inverseJoinColumns = { @JoinColumn(name = "department_id") }
    )
    private Set<Department> departments = new HashSet<>();

    @Transient
    private Long[] orgIds;
}
