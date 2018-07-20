package com.yodean.oa.module.asset.seal.vo;

import com.yodean.oa.module.asset.AssetStatus;
import com.yodean.oa.module.asset.seal.entity.Seal;
import lombok.Data;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.io.Serializable;

/**
 * Created by rick on 7/20/18.
 */
@Data
public class SealVO implements Serializable {
    private Long id;


    /**
     * 印章名称
     */
    private String title;

    /**
     * 保管人
     */

    private Long custodianId;


    private String custodianName;

    /**
     * 关联审批
     */
    private Boolean associateFlow;

    /**
     * 印章状态
     */
    @Enumerated(EnumType.STRING)
    private AssetStatus assetStatus;

    public static SealVO format(Seal seal) {
        SealVO sealVO = new SealVO();
        sealVO.setId(seal.getId());
        sealVO.setTitle(seal.getTitle());
        sealVO.setAssetStatus(seal.getAssetStatus());
        sealVO.setAssociateFlow(seal.getAssociateFlow());
        sealVO.setCustodianId(seal.getCustodian().getId());
        sealVO.setCustodianName(seal.getCustodian().getName());
        return sealVO;
    }
}
