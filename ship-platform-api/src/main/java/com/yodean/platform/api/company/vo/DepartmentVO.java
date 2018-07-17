package com.yodean.platform.api.company.vo;

import com.yodean.platform.domain.Department;
import lombok.Data;

import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Created by rick on 7/17/18.
 */
@Data
public class DepartmentVO implements Serializable {

    private Long id;

    private String name;

    private Set<EmployeeVO> leaders;

    public static DepartmentVO format(Department department) {
        DepartmentVO departmentVO = new DepartmentVO();

        departmentVO.setId(department.getId());
        departmentVO.setName(department.getName());

        Set<EmployeeVO> leaderSet = new LinkedHashSet<>();

        department.getEmployees().forEach(employee -> {
            if (employee.getLeader())
                leaderSet.add(EmployeeVO.format(employee));
        });

        departmentVO.setLeaders(leaderSet);

        return departmentVO;
    }

}
