package com.yodean.oa.module.asset.material.service;

import com.yodean.oa.module.asset.material.entity.MaterialSerialNumber;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * Created by rick on 7/24/18.
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class MaterialSerialNumberServiceTest {

    @Autowired
    private MaterialSerialNumberService materialSerialNumberService;

    @Test
    public void findByMaterialAndStorageAndNumber() throws Exception {
        List<MaterialSerialNumber> byMaterialAndStorageAndNumber =
                materialSerialNumberService.findByMaterialAndStorageAndNumber(153241357921402L, 153240089100179L, null);
        byMaterialAndStorageAndNumber.forEach(materialSerialNumber -> {
            System.out.println(materialSerialNumber.getNumber());
        });
    }
}