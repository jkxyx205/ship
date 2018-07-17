package com.yodean.platform.api.company.service;

import com.yodean.platform.domain.Department;
import com.yodean.platform.domain.Employee;

import java.util.List;

/**
 * Created by rick on 7/16/18.
 */
public interface CompanyService {

    public static final String PATH_SEPARATOR = "/";

    /**
     * 添加组织架构
     * @param department
     * @return
     */
    Department save(Department department);

    /**
     * 根据id删除部门
     * @param id
     */
    void deleteDepartmentById(Long id);

    /**
     *  部门（包括子部门）所有人数量
     * @param id
     */
    int countEmployeeByDepartmentId(Long id);

    /**
     * 部门（包括子部门）所有人
     * @param id
     */
    List<Employee> findEmployeeByDepartmentId(Long id);

}
