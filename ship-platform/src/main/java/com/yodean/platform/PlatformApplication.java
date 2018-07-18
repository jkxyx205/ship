package com.yodean.platform;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

/**
 * Created by rick on 7/16/18.
 */
@SpringBootApplication
@EntityScan("com.yodean")
//@MapperScan("com.yodean.platform.*.mapper")
public class PlatformApplication {

    public static void main(String[] args) {

        SpringApplication.run(PlatformApplication.class, args);

    }
}
