package com.yodean.oa.module.asset.material.service;

import com.yodean.oa.common.core.service.BaseService;
import com.yodean.oa.module.asset.material.entity.Material;
import com.yodean.oa.module.asset.material.entity.MaterialUnit;
import com.yodean.oa.module.asset.material.repository.MaterialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * Created by rick on 7/23/18.
 */
@Service
public class MaterialService extends BaseService<Material, MaterialRepository> {

    @Autowired
    private MaterialUnitService materialUnitService;

    /**
     * 新增
     * @param material
     * @return
     */
    @Override
    public Material save(Material material) {

        MaterialUnit conversionBaseUnit;

        MaterialUnit baseUnit = materialUnitService.findById(material.getBaseUnitId()); //预设基本单位

        conversionBaseUnit = new MaterialUnit();
        conversionBaseUnit.setName(baseUnit.getName());
        conversionBaseUnit.setTitle(baseUnit.getTitle());
        conversionBaseUnit.setDenominator(1);
        conversionBaseUnit.setNumerator(1);
        conversionBaseUnit.setConstant(0d);

        material.setBaseUnit(baseUnit);


        if (Objects.nonNull(material.getId())) {
            Material persist = findById(material.getId());
            material.getUnitCategory().setId(persist.getUnitCategory().getId());
            conversionBaseUnit.setId(persist.getUnitCategory().getBaseUnit().getId());

            material.getUnitCategory().getMaterialUnitList().add(persist.getUnitCategory().getBaseUnit()); //添加基准
        }

        material.getUnitCategory().setBaseUnit(conversionBaseUnit);

        return super.save(material);
    }

}
