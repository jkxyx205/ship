package com.yodean.oa.module.asset.material.repository;

import com.yodean.oa.module.asset.material.entity.Inventory;
import com.yodean.oa.module.asset.material.entity.Material;
import com.yodean.oa.module.asset.material.entity.Storage;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by rick on 7/24/18.
 */
public interface InventoryRepository extends JpaRepository<Inventory, Long> {

    Inventory findByMaterialAndStorage(Material material, Storage storage);
}
