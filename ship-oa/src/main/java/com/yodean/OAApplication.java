package com.yodean;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Created by rick on 7/13/18.
 */
@SpringBootApplication
public class OAApplication {
    public static void main(String[] args) {

        SpringApplication.run(OAApplication.class, args);//.getBean(RemoteServiceInterface.class).getSession("", "");
    }
}
