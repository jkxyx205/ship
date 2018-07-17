package com.yodean.platform.config;

import com.rick.db.service.BaseService;
import com.yodean.common.enums.DelFlag;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.type.EnumOrdinalTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandlerRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.context.annotation.Configuration;

/**
 * Created by rick on 7/17/18.
 */
@Configuration
@ImportAutoConfiguration({BaseService.class})
public class DbConfig {

    @Autowired
    private void specialTypeHandlerRegistry(SqlSessionFactory sqlSessionFactory) {
        TypeHandlerRegistry typeHandlerRegistry = sqlSessionFactory.getConfiguration().getTypeHandlerRegistry();
        typeHandlerRegistry.register(DelFlag.class, JdbcType.INTEGER, EnumOrdinalTypeHandler.class);
    }

}
