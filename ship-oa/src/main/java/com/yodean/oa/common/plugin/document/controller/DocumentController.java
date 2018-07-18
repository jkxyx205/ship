package com.yodean.oa.common.plugin.document.controller;

import com.yodean.common.dto.Result;
import com.yodean.common.util.ResultUtils;
import com.yodean.oa.common.plugin.document.entity.Document;
import com.yodean.oa.common.plugin.document.enums.DocumentCategory;
import com.yodean.oa.common.plugin.document.service.DocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.IOException;
import java.util.List;

/**
 * Created by rick on 7/18/18.
 */
@RestController
@RequestMapping("/documents")
public class DocumentController {
    private static final String UPLOAD_NAME = "upload";

    private static final String UPLOAD_PATH = "document";

    @Autowired
    private DocumentService documentService;

    /***
     * 上传附件
     * @param multipartRequest
     * @param categoryId
     * @return
     * @throws IOException
     */
    @PostMapping("/{category}/{categoryId}/upload")
    public Result<List<Document>> upload(MultipartHttpServletRequest multipartRequest, @PathVariable DocumentCategory category, @PathVariable Long categoryId, Long parentId) throws IOException {
        List<MultipartFile> files = multipartRequest.getFiles(UPLOAD_NAME);
        return ResultUtils.success(documentService.upload(files, UPLOAD_PATH, parentId, category, categoryId));
    }

    /***
     * 上传附件
     * @param multipartRequest
     * @return
     * @throws IOException
     */
    @PostMapping("/{category}/upload")
    public Result<List<Document> > upload2(MultipartHttpServletRequest multipartRequest, @PathVariable DocumentCategory category, Long parentId) throws IOException {
        return upload(multipartRequest, category, null, parentId);
    }
}
