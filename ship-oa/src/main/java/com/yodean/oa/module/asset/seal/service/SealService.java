package com.yodean.oa.module.asset.seal.service;

import com.yodean.oa.common.core.service.BaseService;
import com.yodean.oa.module.asset.AssetStatus;
import com.yodean.oa.module.asset.seal.entity.Seal;
import com.yodean.oa.module.asset.seal.entity.SealLog;
import com.yodean.oa.module.asset.seal.repository.SealLogRepository;
import com.yodean.oa.module.asset.seal.repository.SealRepository;
import com.yodean.platform.domain.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;

/**
 * Created by rick on 7/20/18.
 */
@Service
public class SealService extends BaseService<Seal, SealRepository> {

    @Autowired
    private SealLogRepository sealLogRepository;


    /**
     * 借用|归还
     * @param assetStatus
     */
    @Transactional
    public void setSealStatus(Long id, AssetStatus assetStatus) {
        Seal seal = findById(id);
        seal.setAssetStatus(assetStatus);
        jpaRepository.save(seal);

        SealLog sealLog = new SealLog();
        sealLog.setSeal(seal);
        sealLog.setContent(String.format("%s在%s%s印章【%s】", UserUtils.getCurrentEmployee().getName()
                                                    ,new Date(), assetStatus.getDescription(), seal.getTitle()));

        sealLogRepository.save(sealLog);
    }

}
