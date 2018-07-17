package com.yodean.platform;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Created by rick on 7/16/18.
 */
@SpringBootApplication
@MapperScan("com.yodean.platform.*.mapper")
public class PlatformApplication {

    public static void main(String[] args) {

        SpringApplication.run(PlatformApplication.class, args);

    }
}
