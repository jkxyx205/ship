package com.yodean.oa.common.config;

import com.rick.db.service.JdbcService;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.context.annotation.Configuration;

/**
 * Created by rick on 7/19/18.
 */
@Configuration
@ImportAutoConfiguration({JdbcService.class})
public class DbConfig {
}
