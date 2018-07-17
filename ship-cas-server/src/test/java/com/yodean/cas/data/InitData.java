package com.yodean.cas.data;

import com.yodean.common.Constants;
import com.yodean.common.PasswordUtils;
import com.yodean.module.app.service.AppService;
import com.yodean.module.user.service.UserService;
import com.yodean.platform.domain.App;
import com.yodean.platform.domain.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by rick on 7/13/18.
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class InitData {

    @Autowired
    private UserService userService;

    @Autowired
    private AppService appService;

    @Test
    public void testInitData() {
        initUser();
//        initApps();
    }

    private void initUser() {
        User user = new User();
        user.setNickname("rick");
        user.setPassword("123456");
        PasswordUtils.encryptPassword(user);
        user.setLocked(false);

        userService.save(user);

    }

    private void initApps() {
        App app = new App();
        app.setName("OA");
        app.setAppKey(Constants.SERVER_APP_KEY);
        app.setAppSecret(Constants.SERVER_APP_KEY);
        app.setAvailable(true);

        appService.save(app);
    }
}
