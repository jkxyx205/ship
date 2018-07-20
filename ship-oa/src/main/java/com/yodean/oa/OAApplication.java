package com.yodean.oa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

/**
 * Created by rick on 7/13/18.
 */
@SpringBootApplication
@EntityScan("com.yodean")
public class OAApplication {
    public static void main(String[] args) {
        SpringApplication.run(OAApplication.class, args);
    }
}
