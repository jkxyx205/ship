package com.yodean.oa.module.asset.material.repository;

import com.yodean.oa.module.asset.material.entity.MaterialSerialNumber;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * Created by rick on 7/24/18.
 */
public interface MaterialSerialNumberRepository extends JpaRepository<MaterialSerialNumber, Long>, JpaSpecificationExecutor<MaterialSerialNumber> {

}
