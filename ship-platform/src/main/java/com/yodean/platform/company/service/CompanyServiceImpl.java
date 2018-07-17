package com.yodean.platform.company.service;

import com.yodean.platform.api.company.service.CompanyService;
import com.yodean.platform.domain.Department;
import com.yodean.platform.domain.Employee;
import com.yodean.platform.user.repository.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by rick on 7/16/18.
 */
@Service
public class CompanyServiceImpl implements CompanyService {

//    @Autowired
//    private CompanyRepository companyRepository;

    @Autowired
    private DepartmentRepository departmentRepository;




    /**
     * 新增/修改部门
     * @param department
     * @return
     */
    @Override
    public Department save(Department department) {
        //获取path
        Department parentDept = departmentRepository.getOne(department.getParentId());
        String path = parentDept.getPath() + CompanyService.PATH_SEPARATOR + parentDept.getId() +  CompanyService.PATH_SEPARATOR;
        department.setPath(path);

        return departmentRepository.save(department);
    }

    @Override
    public void deleteDepartmentById(Long id) {

    }

    @Override
    public int countEmployeeByDepartmentId(Long id) {
        return 0;
    }

    @Override
    public List<Employee> findEmployeeByDepartmentId(Long id) {
        return null;
    }
}
