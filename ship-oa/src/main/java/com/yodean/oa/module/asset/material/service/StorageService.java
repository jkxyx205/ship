package com.yodean.oa.module.asset.material.service;

import com.yodean.oa.common.core.service.BaseService;
import com.yodean.oa.module.asset.material.entity.Storage;
import com.yodean.oa.module.asset.material.repository.StorageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

/**
 * Created by rick on 7/20/18.
 */
@Service
public class StorageService extends BaseService<Storage> {

    @Autowired
    private StorageRepository storageRepository;

    @Override
    protected JpaRepository<Storage, Long> autowired() {
        return storageRepository;
    }
}
