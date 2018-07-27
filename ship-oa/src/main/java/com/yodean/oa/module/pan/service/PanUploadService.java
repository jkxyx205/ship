package com.yodean.oa.module.pan.service;

import com.yodean.oa.common.plugin.document.service.DocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * All rights Reserved, Designed By www.xhope.top
 *
 * @version V1.0
 * @Description: (用一句话描述该文件做什么)
 * @author: Rick.Xu
 * @date: 7/26/18 12:23
 * @Copyright: 2018 www.yodean.com. All rights reserved.
 */
@Service
public class PanUploadService {

    @Autowired
    private DocumentService documentService;

    @Autowired
    private PanAuthorityService panAuthorityService;


}
