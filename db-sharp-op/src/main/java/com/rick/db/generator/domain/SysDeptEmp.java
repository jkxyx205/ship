package com.rick.db.generator.domain;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "sys_dept_emp")
public class SysDeptEmp {
    @Id
    @Column(name = "employee_id")
    private Long employeeId;

    @Id
    @Column(name = "department_id")
    private Long departmentId;

    /**
     * @return employee_id
     */
    public Long getEmployeeId() {
        return employeeId;
    }

    /**
     * @param employeeId
     */
    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    /**
     * @return department_id
     */
    public Long getDepartmentId() {
        return departmentId;
    }

    /**
     * @param departmentId
     */
    public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
    }
}