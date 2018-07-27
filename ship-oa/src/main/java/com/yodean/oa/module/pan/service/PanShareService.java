package com.yodean.oa.module.pan.service;

import com.yodean.oa.common.core.service.BaseService;
import com.yodean.oa.common.plugin.document.entity.Document;
import com.yodean.oa.common.plugin.document.service.DocumentService;
import com.yodean.oa.module.pan.entity.PanShare;
import com.yodean.oa.module.pan.repository.PanShareRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * All rights Reserved, Designed By www.xhope.top
 *
 * @version V1.0
 * @Description: (用一句话描述该文件做什么)
 * @author: Rick.Xu
 * @date: 7/26/18 12:28
 * @Copyright: 2018 www.yodean.com. All rights reserved.
 */
@Service
public class PanShareService extends BaseService<PanShare> {
    @Autowired
    private PanShareRepository panShareRepository;

    @Autowired
    private DocumentService documentService;


    @Override
    protected JpaRepository<PanShare, Long> autowired() {
        return panShareRepository;
    }

    /**
     * 添加共享文件夹
     * @param panShare
     * @return
     */
    @Transactional
    @Override
    public PanShare save(PanShare panShare) {
        panShare = super.save(panShare);
        Document document = panShare.getDocument();
        document.setCategoryId(panShare.getId());
        documentService.save(document);
        return panShare;
    }

    /**
     * 删除共享文件夹
     * @param shareId
     */
    @Transactional
    public void deletePanShare(Long shareId) {
        Document document = findById(shareId).getDocument();
        documentService.deleteByFlag(document.getId());
        deleteByFlag(shareId);
    }
}
