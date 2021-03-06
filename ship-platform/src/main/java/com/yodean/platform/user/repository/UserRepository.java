package com.yodean.platform.user.repository;

import com.yodean.platform.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by rick on 7/16/18.
 */
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * 根据电话号码查找用户
     * @param tel
     * @return
     */
    User findByTel(String tel);
}
