package com.yodean.platform.user.controller;

import com.yodean.common.dto.Result;
import com.yodean.common.util.ResultUtils;
import com.yodean.platform.api.user.service.UserService;
import com.yodean.platform.domain.Employee;
import com.yodean.platform.user.RegisterUserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by rick on 7/16/18.
 */
@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 用户注册
     * @param registerUserDTO
     * @return
     */
    @PostMapping("/register")
    public Result registerUser(@RequestBody RegisterUserDTO registerUserDTO) {
        userService.registerUser(registerUserDTO.getUser(), registerUserDTO.getCompany());
        return ResultUtils.success();
    }

    /**
     * 获取员工信息
     * @param id
     * @return
     */
    @GetMapping("employees/{id}")
    public Result findEmployeeById(@PathVariable Long id) {
        Employee employee = userService.findEmployeeById(id);

//        System.out.println(employee.getDepartments().size());

        return ResultUtils.success(employee.getName());
    }

    /**
     * 编辑员工信息
     * @param employee
     * @return
     */
    @PostMapping("employees/{id}")
    public Result updateEmployee(@PathVariable Long id, @RequestBody Employee employee) {
        employee.setId(id);
        userService.save(employee);
        return ResultUtils.success();
    }


}
