package com.yodean.platform.company.controller;

import com.yodean.common.dto.Result;
import com.yodean.common.util.ResultUtils;
import com.yodean.platform.api.company.service.CompanyService;
import com.yodean.platform.domain.Department;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by rick on 7/16/18.
 */
@RestController
@RequestMapping("/companies")
public class CompanyController {

    @Autowired
    private CompanyService companyService;

    /**
     *
     * @return
     */
    @PostMapping("/departments")
    public Result saveOrganization(@RequestBody Department department) {
        companyService.save(department);
        return ResultUtils.success();
    }

}
