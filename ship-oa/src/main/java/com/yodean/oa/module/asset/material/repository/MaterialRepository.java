package com.yodean.oa.module.asset.material.repository;

import com.yodean.oa.module.asset.material.entity.Material;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by rick on 7/23/18.
 */
public interface MaterialRepository extends JpaRepository<Material, Long> {
}
