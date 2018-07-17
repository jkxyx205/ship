package com.yodean.platform.company.service;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.rick.db.service.BaseService;
import com.rick.db.util.EntityBeanUtils;
import com.yodean.common.enums.DelFlag;
import com.yodean.platform.api.company.service.CompanyService;
import com.yodean.platform.api.company.vo.DepartmentVO;
import com.yodean.platform.api.company.vo.EmployeeVO;
import com.yodean.platform.company.repository.CompanyRepository;
import com.yodean.platform.domain.Department;
import com.yodean.platform.domain.Employee;
import com.yodean.platform.domain.User;
import com.yodean.platform.user.repository.DepartmentRepository;
import com.yodean.platform.user.repository.EmployeeRepository;
import com.yodean.platform.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

/**
 * Created by rick on 7/16/18.
 */
@Service
public class CompanyServiceImpl implements CompanyService {

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BaseService baseService;



    /**
     * 新增/修改部门
     * @param department
     * @return
     */
    @Override
    public Department save(Department department) {
        //获取path
        Department parentDept = departmentRepository.getOne(department.getParentId());
        String path = parentDept.getPath() + parentDept.getId() +  CompanyService.PATH_SEPARATOR;
        department.setPath(path);

        return departmentRepository.save(department);
    }

    /**
     * 删除部门
     * @param id
     */
    @Override
    public void deleteDepartmentById(Long id) {
        if (countEmployeeByDepartmentId(id) > 0) {
            throw new RuntimeException("先删除用户，再删除部门");
        }

        Department department = departmentRepository.getOne(id);

        department.setDelFlag(DelFlag.DEL_FLAG_CLEAN);
        departmentRepository.save(department);

//        departmentRepository.deleteById(id);
    }

    /**
     * 获取部门详情
     * @param id
     * @return
     */
    @Override
    public DepartmentVO findDepartmentById(Long id) {
        Department department = departmentRepository.getOne(id);
        return DepartmentVO.format(department);
    }

    /**
     * 查找部门及以下部门的人数
     * @param id
     * @return
     */
    @Override
    public int countEmployeeByDepartmentId(Long id) {
        String query = "com.yodean.platform.user.mapper.DepartmentMapper.countEmployeeByDepartmentId";
        Map<String, Object> params = Maps.newHashMapWithExpectedSize(2);
        params.put("id", id);
        params.put("id2", id);

        return baseService.query(baseService.getSQL(query), params, Integer.class).get(0);
    }

    /**
     * 部门下所有的员工
     * @param id
     * @return
     */

    @Override
    public Set<EmployeeVO> findEmployeeByDepartmentId(Long id) {
        List<Employee> employees = findEmployeeByDepartmentId2(id);

        Set<EmployeeVO> result =Sets.newLinkedHashSetWithExpectedSize(employees.size());

        employees.forEach(employee -> {
            result.add(EmployeeVO.format(employee));
        });

        return result;
    }

    private void getEmployees(Department department, List list) {
        list.addAll(department.getEmployees());

        for (Department subDept : department.getSubDept()) {
            getEmployees(subDept, list);
        }
    }

    private List<Employee> findEmployeeByDepartmentId2(Long id) {
        Department department = departmentRepository.getOne(id);

        List<Employee> list = new ArrayList<>();

        getEmployees(department, list);

        return list;
    }



    @Override
    public EmployeeVO findEmployeeById(Long id) {
        Employee employee = employeeRepository.getOne(id);
        return EmployeeVO.format(employee);
    }

    @Override
    @Transactional
    public Employee save(Employee employee) {
        if (Objects.nonNull(employee.getId())) { //编辑
            Employee persist = employeeRepository.getOne(employee.getId());
            EntityBeanUtils.merge(persist, employee, false );
            employee = persist;

            //如果是未激活的用户，可以修改手机号码
            //TODO
        } else { //新增用户
            String tel = employee.getUser().getTel();
            employee.setLeader(false);
            User user = userRepository.findByTel(tel);
            if (Objects.isNull(user)) { //发送随机密码
                user = new User();
                user.setNickname(employee.getName());
                user.setLocked(false);
                user.setPassword("123456");
                user.setSex('2');
                user.setSalt("2323");
                user.setTel(tel);

                userRepository.save(user);
            }

            employee.setUser(user);

        }

        employeeRepository.save(employee);
        return employee;
    }

    @Override
    public void deleteEmployeeById(Long id) {
        Employee employee = employeeRepository.getOne(id);
        employee.setDelFlag(DelFlag.DEL_FLAG_CLEAN);
        employeeRepository.save(employee);
    }
}
