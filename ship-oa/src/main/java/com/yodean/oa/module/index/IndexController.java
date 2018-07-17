package com.yodean.oa.module.index;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by rick on 7/13/18.
 */
@Controller
@RequestMapping
public class IndexController {

    @GetMapping
    public String gotoIndexPage() {
        return "index";
    }

    @GetMapping("/login")
    public String gotoLoginPage() {
        return "login";
    }


    @PostMapping("/login")
    public String loginFail() {
        return "login";
    }
}
