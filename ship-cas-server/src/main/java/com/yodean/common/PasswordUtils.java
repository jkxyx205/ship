package com.yodean.common;

import com.yodean.platform.domain.User;
import org.apache.shiro.crypto.RandomNumberGenerator;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;

/**
 * Created by rick on 7/13/18.
 */
public final class PasswordUtils {
    private static final RandomNumberGenerator randomNumberGenerator = new SecureRandomNumberGenerator();

    private static final String algorithmName = "md5";
    private static final int hashIterations = 2;


    public static void encryptPassword(User user) {

        user.setSalt(user.getNickname() + randomNumberGenerator.nextBytes().toHex());

        String newPassword = new SimpleHash(
                algorithmName,
                user.getPassword(),
                ByteSource.Util.bytes(user.getSalt()),
                hashIterations).toHex();

        user.setPassword(newPassword);
    }


}
