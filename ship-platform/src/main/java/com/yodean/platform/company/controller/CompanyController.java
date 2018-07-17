package com.yodean.platform.company.controller;

import com.yodean.common.dto.Result;
import com.yodean.common.util.ResultUtils;
import com.yodean.platform.api.company.service.CompanyService;
import com.yodean.platform.api.company.vo.DepartmentVO;
import com.yodean.platform.domain.Department;
import com.yodean.platform.domain.Employee;
import com.yodean.platform.api.company.vo.EmployeeVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

/**
 * Created by rick on 7/16/18.
 */
@RestController
@RequestMapping("/companies")
public class CompanyController {

    @Autowired
    private CompanyService companyService;

    /**
     * 添加部门
     * @return
     */
    @PostMapping("/departments")
    public Result saveDepartment(@RequestBody Department department) {
        companyService.save(department);
        return ResultUtils.success();
    }

    /**
     * 删除部门
     * @param id
     * @return
     */
    @DeleteMapping("/departments/{id}")
    public Result deleteDepartment(@PathVariable long id) {
        companyService.deleteDepartmentById(id);
        return ResultUtils.success();
    }


    @GetMapping("/departments/{id}")
    public Result<DepartmentVO> findDepartmentById(@PathVariable long id) {
        DepartmentVO departmentVO = companyService.findDepartmentById(id);
        return ResultUtils.success(departmentVO);
    }


    /**
     * 某部门下所有员工
     * @param id
     * @return
     */
    @GetMapping("/departments/{id}/employees")
    public Result<List<EmployeeVO>> findEmployeeByDepartmentId(@PathVariable long id) {
        Set<EmployeeVO> list = companyService.findEmployeeByDepartmentId(id);
        return ResultUtils.success(list);
    }

    /**
     * 获取员工信息
     * @param id
     * @return
     */
    @GetMapping("employees/{id}")
    public Result<EmployeeVO> findEmployeeById(@PathVariable Long id) {
        EmployeeVO employeeVO = companyService.findEmployeeById(id);

        return ResultUtils.success(employeeVO);
    }

    /**
     * 新增员工
     * @param employee
     * @return
     */
    @PostMapping("/employees")
    public Result saveEmployee(@RequestBody Employee employee) {
        companyService.save(employee);
        return ResultUtils.success();
    }

    /**
     * 编辑员工
     * @param employee
     * @return
     */
    @PostMapping("/employees/{id}")
    public Result updateEmployee(@PathVariable Long id, @RequestBody Employee employee) {
        employee.setId(id);
        companyService.save(employee);
        return ResultUtils.success();
    }

    /**
     * 删除员工
     * @param id
     * @return
     */
    @DeleteMapping("/employees/{id}")
    public Result deleteEmployeeById(@PathVariable Long id) {
        companyService.deleteEmployeeById(id);

        return ResultUtils.success();
    }

}
