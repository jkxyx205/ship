package com.yodean.oa.module.asset.material.service;

import com.yodean.oa.common.core.service.BaseService;
import com.yodean.oa.common.exception.OAException;
import com.yodean.oa.common.plugin.document.enums.ExceptionCode;
import com.yodean.oa.module.asset.material.MaterialUnitUtils;
import com.yodean.oa.module.asset.material.entity.MaterialUnit;
import com.yodean.oa.module.asset.material.entity.UnitCategory;
import com.yodean.oa.module.asset.material.repository.MaterialUnitRepository;
import com.yodean.oa.module.asset.material.repository.UnitCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Created by rick on 7/23/18.
 */
@Service
public class MaterialUnitService extends BaseService<MaterialUnit, MaterialUnitRepository> {

    @Autowired
    private UnitCategoryRepository unitCategoryRepository;

    /**
     * 新增新的维度
     */
    public UnitCategory addUnitCategory(UnitCategory unitCategory) {
        unitCategory.initParams();
        unitCategory = unitCategoryRepository.save(unitCategory);
        return unitCategory;
    }

    /**
     * @param convertNumber 转换数量
     * @param srcUnitId     原单位
     * @param distUnitId    需要转换单位
     * @return
     */
    public double convert(Double convertNumber, Long srcUnitId, Long distUnitId) {
        if (srcUnitId.intValue() == distUnitId.intValue()) return convertNumber;

        MaterialUnit srcUnit = findById(srcUnitId);
        MaterialUnit distUnit = findById(distUnitId);

        return convertNumber * MaterialUnitUtils.calculate(srcUnit, distUnit);
    }

    /**
     * 获取维度列表
     * @param id
     * @return
     */
    public UnitCategory findUnitCategoryById(Long id) {

        Optional<UnitCategory> optional = unitCategoryRepository.findById(id);
        if (!optional.isPresent()) {
            throw new OAException(ExceptionCode.NOT_FOUND_ERROR);
        }

        return optional.get();

    }
}
