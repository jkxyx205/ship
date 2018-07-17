package com.yodean.platform.api.company.vo;

import com.yodean.platform.domain.Employee;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

/**
 * Created by rick on 7/17/18.
 */
@Getter
@Setter
public class EmployeeVO implements Serializable {

    private Long id;

    private String name;

    private String tel;

    private String email;

    private String position;

    private String departments;

    private String departmentIds;

    public static EmployeeVO format(Employee employee) {
        EmployeeVO employeeVO = new EmployeeVO();

        employeeVO.setId(employee.getId());
        employeeVO.setTel(employee.getUser().getTel());
        employeeVO.setEmail(employee.getEmail());
        employeeVO.setName(employee.getName());
        employeeVO.setPosition(employee.getPosition());
        employeeVO.setDepartments("");
        employeeVO.setDepartmentIds("");

        employee.getDepartments().forEach(department -> {
            employeeVO.setDepartments(employeeVO.getDepartments() + department.getName() + "；");
            employeeVO.setDepartmentIds(employeeVO.getDepartmentIds() + department.getId() + "；");
        });

        return employeeVO;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EmployeeVO)) return false;

        EmployeeVO that = (EmployeeVO) o;

        if (Objects.equals(that.getId(), this.id))
            return true;

        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
