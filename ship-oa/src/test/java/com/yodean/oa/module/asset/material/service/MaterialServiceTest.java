package com.yodean.oa.module.asset.material.service;

import com.yodean.oa.module.asset.material.entity.Material;
import com.yodean.oa.module.asset.material.entity.MaterialUnit;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by rick on 7/23/18.
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class MaterialServiceTest {

    @Autowired
    private MaterialService materialService;

    @Test
    public void testFindById() {
        Material material = materialService.findById(153233631545238L);

        System.out.println("title: " + material.getTitle());

        System.out.println("baseUnit: "+ material.getBaseUnit().getTitle());

        for (MaterialUnit materialUnit : material.getUnitCategory().getMaterialUnitList()) {
            System.out.println("unit title: "  + materialUnit.getTitle() + "=>" + materialUnit.getConversionText());
        }
    }

}