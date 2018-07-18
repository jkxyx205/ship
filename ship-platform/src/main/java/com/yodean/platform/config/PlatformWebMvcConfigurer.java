package com.yodean.platform.config;

import com.yodean.dictionary.StringToDictConverter;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Created by rick on 7/18/18.
 */
@Configuration
public class PlatformWebMvcConfigurer implements WebMvcConfigurer {
    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new StringToDictConverter());
    }
}
