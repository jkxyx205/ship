package com.yodean.platform.api.company.service;

import com.yodean.platform.api.company.vo.DepartmentVO;
import com.yodean.platform.api.company.vo.EmployeeVO;
import com.yodean.platform.domain.Department;
import com.yodean.platform.domain.Employee;

import java.util.List;
import java.util.Set;

/**
 * Created by rick on 7/16/18.
 */
public interface CompanyService {

    public static final String PATH_SEPARATOR = "/";

    /**
     * 添加部门
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
     * 根据id查看部门详情
     * @param id
     */
    DepartmentVO findDepartmentById(Long id);

    /**
     *  部门（包括子部门）所有人数量
     * @param id
     */
    int countEmployeeByDepartmentId(Long id);

    /**
     * 部门（包括子部门）所有人
     * @param id
     */
    Set<EmployeeVO> findEmployeeByDepartmentId(Long id);

    /**
     * 查找员工
     * @param id
     * @return
     */
    EmployeeVO findEmployeeById(Long id);

    /**
     * 保存或编辑员工
     * @param employee
     * @return
     */
    Employee save(Employee employee);

    /**
     * 根据id删除用户
     * @param id
     */
    void deleteEmployeeById(Long id);
}
