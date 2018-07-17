package com.yodean.config;

import com.yodean.cas.api.RemoteServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.remoting.httpinvoker.HttpInvokerServiceExporter;

/**
 * Created by rick on 7/13/18.
 */
@Configuration
public class ServiceExporter {

    @Autowired
    private RemoteServiceInterface remoteService;

    @Bean(name = "/remoteService")
    public HttpInvokerServiceExporter remoteServiceExporter() {
        HttpInvokerServiceExporter httpInvokerServiceExporter = new HttpInvokerServiceExporter();
        httpInvokerServiceExporter.setService(remoteService);
        httpInvokerServiceExporter.setServiceInterface(RemoteServiceInterface.class);

        return httpInvokerServiceExporter;
    }
}
