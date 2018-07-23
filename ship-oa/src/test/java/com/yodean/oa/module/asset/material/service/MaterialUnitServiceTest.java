package com.yodean.oa.module.asset.material.service;


import com.yodean.oa.module.asset.material.entity.Material;
import com.yodean.oa.module.asset.material.entity.MaterialUnit;
import com.yodean.oa.module.asset.material.entity.UnitCategory;
import org.junit.Assert;
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
public class MaterialUnitServiceTest {

    @Autowired
    private MaterialUnitService materialUnitService;

    @Autowired
    private MaterialService materialService;


    @Test
    public void convert() throws Exception {
        double result = materialUnitService.convert(1.0d, 153232586999484L, 153231863873066L);
        Assert.assertEquals("", 1000d, result, 0.0);
    }

    @Test
    public void convertKmDm() throws Exception {
        double result = materialUnitService.convert(1.0d, 153232586999484L, 153231921496160L);
        Assert.assertEquals("", 10000d, result, 0.0);
    }

    @Test
    public void convertDmm() throws Exception {
        double result = materialUnitService.convert(1.0d, 153231921496160L, 153231863873066L);
        Assert.assertEquals("", 0.1d, result, 0.0);
    }

    @Test
    public void testList() {
        UnitCategory unitCategory = materialUnitService.findUnitCategoryById(153231863872085L);
        System.out.println(unitCategory.getTitle());
        for (MaterialUnit materialUnit : unitCategory.getMaterialUnitList()) {
            System.out.println(materialUnit.getTitle() + " : " + materialUnit.getConversionText() + " : " + materialUnit.getConversionFullText());
        }
    }

    @Test
    public void testMaterialMain() {
        Material material = materialService.findById(153235659510915L);
        System.out.println(material.getTitle());
        System.out.println(material.getBaseUnit().getTitle());
        for (MaterialUnit materialUnit : material.getUnitCategory().getMaterialUnitList()) {
            System.out.println(materialUnit.getTitle() + " : " + materialUnit.getConversionText() + " : " + materialUnit.getConversionFullText());
        }

    }
}

