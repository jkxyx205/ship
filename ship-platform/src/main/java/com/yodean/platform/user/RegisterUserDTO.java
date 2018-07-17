package com.yodean.platform.user;

import com.yodean.platform.domain.Company;
import com.yodean.platform.domain.User;
import lombok.Data;

/**
 * Created by rick on 7/16/18.
 */
@Data
public class RegisterUserDTO {

    private User user;

    private Company company;
}
