package com.yodean.platform;

import com.rick.db.service.JdbcService;
import com.yodean.platform.domain.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * Created by rick on 7/17/18.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserTest {

    @Autowired
    private JdbcService baseService;


//    @Autowired
//    private UserMapper userMapper;


    @Test
    public void testUser() {
        String sql = baseService.getSQL("com.yodean.platform.user.mapper.UserMapper.user");

        System.out.println(sql);

        List<User> users = baseService.query(sql, null, User.class);
        for (User user : users) {
            System.out.println("->" + user.getNickname() + ": " + user.getDelFlag().name());
        }
    }

    @Test
    public void testFindUser() {
        User user = new User();

//        user = userMapper.selectOne(user);

        System.out.println("->" + user.getNickname() + ": " + user.getDelFlag().name());
    }
}
