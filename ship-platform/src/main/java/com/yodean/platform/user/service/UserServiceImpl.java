package com.yodean.platform.user.service;

import com.yodean.platform.api.company.service.CompanyService;
import com.yodean.platform.api.user.service.UserService;
import com.yodean.platform.company.repository.CompanyRepository;
import com.yodean.platform.domain.Company;
import com.yodean.platform.domain.Department;
import com.yodean.platform.domain.Employee;
import com.yodean.platform.domain.User;
import com.yodean.platform.user.repository.DepartmentRepository;
import com.yodean.platform.user.repository.EmployeeRepository;
import com.yodean.platform.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * Created by rick on 7/16/18.
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private EmployeeRepository employeeRepository;


    @Transactional
    @Override
    public void registerUser(User user, Company company) {
        // 添加注册用户
        user.setLocked(false);
        user.setSalt("xxxxxx");
//        user.setSex('2');
        userRepository.save(user);

        // 创建部门
        Department rootDept = new Department();
        rootDept.setName(company.getName());
        rootDept.setPath(CompanyService.PATH_SEPARATOR);
        rootDept.setCompany(company);

        // 添加用户
        Employee admin = new Employee();
        admin.setName(user.getNickname());
        admin.setUser(user);
        admin.setLeader(false);

        admin.getDepartments().add(rootDept);
        rootDept.getEmployees().add(admin);

        company.setAdmin(admin);
        company.getDepartments().add(rootDept);

        // 新建公司
        companyRepository.save(company);
    }
}
