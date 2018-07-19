package com.yodean.platform.user.controller;

import com.yodean.common.dto.Result;
import com.yodean.common.util.ResultUtils;
import com.yodean.platform.api.user.service.UserService;
import com.yodean.platform.user.dto.RegisterUserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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




}
