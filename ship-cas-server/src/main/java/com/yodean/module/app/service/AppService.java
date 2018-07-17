package com.yodean.module.app.service;

import com.yodean.module.app.repository.AppRepository;
import com.yodean.platform.domain.App;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by rick on 7/13/18.
 */
@Service
public class AppService {

    @Autowired
    private AppRepository appRepository;

    public App save(App app) {
        return appRepository.save(app);
    }
}
