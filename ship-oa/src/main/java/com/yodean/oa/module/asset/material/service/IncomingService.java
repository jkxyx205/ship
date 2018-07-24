package com.yodean.oa.module.asset.material.service;

import com.yodean.oa.common.core.service.BaseService;
import com.yodean.oa.common.exception.OAException;
import com.yodean.oa.common.plugin.document.enums.ExceptionCode;
import com.yodean.oa.module.asset.material.MaterialUnitUtils;
import com.yodean.oa.module.asset.material.entity.Incoming;
import com.yodean.oa.module.asset.material.entity.Inventory;
import com.yodean.oa.module.asset.material.entity.Material;
import com.yodean.oa.module.asset.material.entity.MaterialUnit;
import com.yodean.oa.module.asset.material.repository.IncomingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Objects;

/**
 * Created by rick on 7/24/18.
 */
@Service
public class IncomingService extends BaseService<Incoming> {

    @Autowired
    private IncomingRepository incomingRepository;

    @Autowired
    private MaterialService materialService;

    @Autowired
    private MaterialUnitService materialUnitService;

    @Autowired
    private InventoryService inventoryService;

    @Autowired
    private MaterialSerialNumberService materialSerialNumberService;

    @Override
    protected JpaRepository<Incoming, Long> autowired() {
        return incomingRepository;
    }

    /**
     * 入库
     * @param incoming
     * @return
     */
    @Override
    @Transactional
    public Incoming save(Incoming incoming) {
        Material material = materialService.findById(incoming.getMaterialId());

        MaterialUnit srcUnit = materialUnitService.findById(incoming.getMaterialUnitId());

        incoming.setBaseNum(incoming.getNum() * MaterialUnitUtils.calculate(srcUnit, material.getUnitCategory().getBaseUnit()));

        incoming.initParams();


        if (Material.MaterialType.S == material.getMaterialType()) {

            if (incoming.getNum().intValue() != incoming.getMaterialSerialNumberList().size()) { //序列号号和数量不一致
                throw new OAException(ExceptionCode.MATERIAL_INCOMING_SERIAL_NOT_MATCH);
            }

            //序列号管理
            materialSerialNumberService.saveAll(incoming.getMaterialSerialNumberList());
        }

        //添加到库存
        Inventory inventory = inventoryService.findByMaterialAndStorage(material, incoming.getStorage());

        if (Objects.isNull(inventory)) {
            inventory = new Inventory();
            inventory.setMaterial(material);
            inventory.setNum(0d);
            inventory.setStorage(incoming.getStorage());
        }

        inventory.setNum(inventory.getNum() + incoming.getBaseNum());

        inventoryService.save(inventory);

        return super.save(incoming);
    }

}
