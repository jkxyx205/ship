package com.yodean.platform.config;

import com.yodean.dictionary.DictionaryUtils;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * Created by rick on 7/17/18.
 */
@Component
public final class Global implements ApplicationContextAware{
    public static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
        DictionaryUtils.setApplicationContext(applicationContext);
    }

}
