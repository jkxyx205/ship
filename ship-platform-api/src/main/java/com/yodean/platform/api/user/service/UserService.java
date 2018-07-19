package com.yodean.platform.api.user.service;

import com.yodean.platform.domain.Company;
import com.yodean.platform.domain.User;

/**
 * Created by rick on 7/16/18.
 */
public interface UserService {
    /**
     * 注册管理员
     * @param user
     * @param company
     */
    void registerUser(User user, Company company);


}
