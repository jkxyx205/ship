package com.yodean.platform.domain;

/**
 * Created by rick on 7/19/18.
 */
public class UserUtils {

    public static Employee getCurrentEmployee() {
        Employee employee = new Employee();
        employee.setId(153190436203732L);
        employee.setName("李四");

        User user = new User();
        user.setId(153190436201936L);
        employee.setUser(user);
        return employee;
    }
}
