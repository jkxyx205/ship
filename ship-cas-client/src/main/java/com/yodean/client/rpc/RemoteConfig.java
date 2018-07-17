package com.yodean.client.rpc;

import com.yodean.cas.api.RemoteServiceInterface;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.remoting.httpinvoker.HttpInvokerProxyFactoryBean;

/**
 * Created by rick on 7/13/18.
 */

@Configuration
public class RemoteConfig {

    @Value("${client.remote.service.url}")
    private String serviceUrl;

    @Bean
    public HttpInvokerProxyFactoryBean remoteService() {
        HttpInvokerProxyFactoryBean invoker = new HttpInvokerProxyFactoryBean();
        invoker.setServiceUrl(serviceUrl);
        invoker.setServiceInterface(RemoteServiceInterface.class);
        return invoker;
    }

}
