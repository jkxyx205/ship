package com.yodean.oa.module.asset.material.service;

import com.yodean.oa.common.core.service.BaseService;
import com.yodean.oa.module.asset.material.entity.Inventory;
import com.yodean.oa.module.asset.material.entity.Material;
import com.yodean.oa.module.asset.material.entity.Storage;
import com.yodean.oa.module.asset.material.repository.InventoryRepository;
import org.springframework.stereotype.Service;

/**
 * Created by rick on 7/24/18.
 */
@Service
public class InventoryService extends BaseService<Inventory, InventoryRepository> {


    public Inventory findByMaterialAndStorage(Material material, Storage storage) {
        return jpaRepository.findByMaterialAndStorage(material, storage);
    }

}
