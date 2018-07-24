package com.yodean.oa.module.asset.material.service;

import com.yodean.oa.common.core.service.BaseService;
import com.yodean.oa.common.exception.OAException;
import com.yodean.oa.common.plugin.document.enums.ExceptionCode;
import com.yodean.oa.module.asset.dto.MovingDTO;
import com.yodean.oa.module.asset.material.MaterialUnitUtils;
import com.yodean.oa.module.asset.material.entity.*;
import com.yodean.oa.module.asset.material.repository.MovingRepository;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;

/**
 * Created by rick on 7/24/18.
 */
@Service
public class MovingService extends BaseService<Moving> {

    @Autowired
    private MovingRepository movingRepository;

    @Autowired
    private MaterialService materialService;

    @Autowired
    private MaterialUnitService materialUnitService;

    @Autowired
    private InventoryService inventoryService;

    @Autowired
    private MaterialSerialNumberService materialSerialNumberService;

    @Override
    protected JpaRepository<Moving, Long> autowired() {
        return movingRepository;
    }

    @Transactional
    public void moving(MovingDTO movingDTO) {
        if (Objects.equals(movingDTO.getSrcStorage(), movingDTO.getDistStorage())) {
            throw new OAException(ExceptionCode.MATERIAL_INVENTORY_MOVING_SAME_STORAGE);
        }

        if (CollectionUtils.isEmpty(movingDTO.getMovingList())) {
            throw new OAException(ExceptionCode.MATERIAL_INVENTORY_MOVING_EMPTY);
        }

        movingDTO.getMovingList().forEach(moving -> {
            moving.setSrcStorageId(movingDTO.getSrcStorage());
            moving.setDistStorageId(movingDTO.getDistStorage());
            moving(moving);
        });

    }

    private void moving(Moving moving) {
        Material material = materialService.findById(moving.getMaterialId());

        MaterialUnit srcUnit = materialUnitService.findById(moving.getMaterialUnitId());

        moving.setBaseNum(moving.getNum() * MaterialUnitUtils.calculate(srcUnit, material.getUnitCategory().getBaseUnit()));

        moving.initParams();

        if (Material.MaterialType.S == material.getMaterialType()) { //设备

            if (moving.getNum().intValue() != moving.getMaterialSerialNumberIds().size()) { //序列号和数量不一致
                throw new OAException(ExceptionCode.MATERIAL_INCOMING_SERIAL_NOT_MATCH);
            }

            //序列号管理
            List<MaterialSerialNumber> materialSerialNumberList =
                    materialSerialNumberService.findByMaterialAndStorageAndNumber(material.getId(), moving.getSrcStorageId(), moving.getMaterialSerialNumberIds());

            if (materialSerialNumberList.size() != moving.getMaterialSerialNumberIds().size()) { //数据库查找设备
                throw new OAException(ExceptionCode.MATERIAL_INCOMING_SERIAL_NOT_MATCH, "存在无效序列号！");
            }

            materialSerialNumberList.forEach(materialSerialNumber -> materialSerialNumber.setStorage(moving.getDistStorage()));

            materialSerialNumberService.saveAll(materialSerialNumberList);
        }

        //-到库存
        Inventory srcInventory = inventoryService.findByMaterialAndStorage(material, moving.getSrcStorage());
        if (Objects.isNull(srcInventory) || (srcInventory.getNum() < moving.getBaseNum())) {
            throw new OAException(ExceptionCode.MATERIAL_INVENTORY_NOT_ENOUGH);
        }

        srcInventory.setNum(srcInventory.getNum() - moving.getBaseNum());

        Inventory distInventory = inventoryService.findByMaterialAndStorage(material, moving.getDistStorage());

        //+到库存
        if (Objects.isNull(distInventory)) {
            distInventory = new Inventory();
            distInventory.setMaterial(material);
            distInventory.setNum(0d);
            distInventory.setStorage(moving.getDistStorage());
        }

        distInventory.setNum(distInventory.getNum() + moving.getBaseNum());

        inventoryService.save(srcInventory);
        inventoryService.save(distInventory);

        super.save(moving);

    }
}
