package com.yodean.oa.common;

import com.yodean.dictionary.DictionaryUtils;
import org.springframework.beans.BeansException;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * Created by rick on 7/18/18.
 */
@Component
@ConfigurationProperties("configuration")
public final class Global implements ApplicationContextAware {
    public static ApplicationContext applicationContext;

    public static String DOCUMENT;

    public static String CDN;

    public static String TMP;

    public void setDocument(String document) {
        this.DOCUMENT = document;
    }

    public void setCdn(String cdn) {
        this.CDN = cdn;
    }

    public void setTmp(String tmp) {
        Global.TMP = tmp;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
        DictionaryUtils.setApplicationContext(applicationContext);
    }

}
