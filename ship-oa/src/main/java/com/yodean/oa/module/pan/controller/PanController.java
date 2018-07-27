package com.yodean.oa.module.pan.controller;

import com.yodean.common.dto.Result;
import com.yodean.common.util.ResultUtils;
import com.yodean.oa.common.plugin.document.entity.Document;
import com.yodean.oa.module.pan.dto.PanAuthorityDTO;
import com.yodean.oa.module.pan.entity.PanShare;
import com.yodean.oa.module.pan.service.PanAuthorityService;
import com.yodean.oa.module.pan.service.PanShareService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.IOException;
import java.util.List;

/**
 * All rights Reserved, Designed By www.xhope.top
 *
 * @version V1.0
 * @Description: (用一句话描述该文件做什么)
 * @author: Rick.Xu
 * @date: 7/26/18 12:24
 * @Copyright: 2018 www.yodean.com. All rights reserved.
 */
@RestController
@RequestMapping("/pans")
public class PanController {

    @Autowired
    private PanShareService panShareService;

    @Autowired
    private PanAuthorityService panAuthorityService;

    private static final String UPLOAD_NAME = "upload";

    private static final String UPLOAD_PATH = "pan";

    /**
     * 添加共享文件夹
     * @return
     */
    @PostMapping("/shares")
    public Result<Long> addSharePan(@RequestBody PanShare panShare) {
        return ResultUtils.success(panShareService.save(panShare).getDocument().getId());

    }

    /**
     * 删除共享文件夹
     * @return
     */
    @DeleteMapping("/shares/{id}")
    public Result addSharePan(@PathVariable Long id) {
        panShareService.deletePanShare(id);
        return ResultUtils.success();
    }

    /**
     * 创建文件夹
     * categoryId:
     *  公司盘： companyId
     *  个人盘：userId或employId
     *  共享盘：panShare#id
     * @return
     */
    @PostMapping("/documents/{categoryId}")
    public Result<Long> mkdir(@PathVariable Long categoryId, @RequestBody Document document) {
        return ResultUtils.success(panAuthorityService.mkdir(document.getName(), document.getParentId(), categoryId).getId());
    }

    /***
     * 上传文件
     * @param multipartRequest
     * @param categoryId
     * @return
     * @throws IOException
     */
    @PostMapping("/documents/{categoryId}/upload")
    public Result<Long[]> upload(MultipartHttpServletRequest multipartRequest, @PathVariable Long categoryId, Long parentId) throws IOException {
        List<MultipartFile> files = multipartRequest.getFiles(UPLOAD_NAME);
        return ResultUtils.success(panAuthorityService.upload(files, UPLOAD_PATH, parentId, categoryId));
    }

    /**
     * 移动文件
     * @param id
     * @return
     */
    @PostMapping("/documents/{id}/{parentId}/move")
    public Result move(@PathVariable Long id, @PathVariable Long parentId) throws IOException {
        panAuthorityService.move(id, parentId);
        return ResultUtils.success();
    }

    /**
     * 复制文件
     * @param id
     * @return
     */
    @PostMapping("/documents/{id}/{parentId}/copy")
    public Result copy(@PathVariable Long id, @PathVariable Long parentId) throws IOException {
        panAuthorityService.copy(id, parentId);
        return ResultUtils.success();
    }



    /**
     * 授权
     * @param
     * @return
     */
    @PostMapping("/documents/{id}/authorize")
    public Result authorize(@PathVariable Long id, @RequestBody PanAuthorityDTO panAuthorityDTO) {
        panAuthorityDTO.setDocumentId(id);
        panAuthorityService.authorize(panAuthorityDTO);

        return ResultUtils.success();
    }
}
