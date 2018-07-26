package com.yodean.oa.common.plugin.document.controller;

import com.yodean.common.dto.Result;
import com.yodean.common.util.ResultUtils;
import com.yodean.oa.common.plugin.document.entity.Document;
import com.yodean.oa.common.plugin.document.service.DocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
     * 上传附件，到实例上
     * @param multipartRequest
     * @param categoryId
     * @return
     * @throws IOException
     */
    @PostMapping("/{categoryId}/upload")
    public Result<List<Document>> upload(MultipartHttpServletRequest multipartRequest, @PathVariable Long categoryId, Long parentId) throws IOException {
        List<MultipartFile> files = multipartRequest.getFiles(UPLOAD_NAME);
        return ResultUtils.success(documentService.upload(files, UPLOAD_PATH, parentId, categoryId));
    }

    /***
     * 上传附件，到分类
     * @param multipartRequest
     * @return
     * @throws IOException
     */
    @PostMapping("/upload")
    public Result<List<Document> > upload2(MultipartHttpServletRequest multipartRequest, Long parentId) throws IOException {
        return upload(multipartRequest,null, parentId);
    }

    /**
     *
     * @param fileFullName 文件夹名称
     * @param parentId 父id
     * @return
     */
    @PostMapping("/mkdir")
    public Result<Long> mkdir(String fileFullName, Long parentId) {
        Document folder = documentService.mkdir(fileFullName, parentId, null);
        return ResultUtils.success(folder.getId());
    }

    /**
     * 重命名
     * @param id
     * @param fileFullName
     * @return
     */
    @PutMapping("/{id}/rename")
    public Result rename(@PathVariable Long id,  String fileFullName) {
        documentService.rename(id, fileFullName);
        return ResultUtils.success();
    }

    @GetMapping("/{id}")
    public Result<Document> findById(@PathVariable Long id) {
        return ResultUtils.success(documentService.findById(id));
    }



    @GetMapping("/list/{id}")
    public Result<List<Document>> listFile(@PathVariable Long id) {
        return ResultUtils.success(documentService.findSubDocument(id));
    }

    /**
     * 删除文件
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Long id) {
        documentService.deleteByFlag(id);
        return ResultUtils.success();
    }

    /**
     * 还原
     * @return
     */
    @PutMapping("/{id}/back")
    public Result putBack(@PathVariable Long id) {
        documentService.putBack(id);
        return ResultUtils.success();
    }

    /**
     * 回收站彻底删除文件
     * @param id
     * @return
     */
    @DeleteMapping("/{id}/clean")
    public Result clean(@PathVariable Long id) {
        documentService.clean(id);
        return ResultUtils.success();
    }

    /**
     * 下载文件
     * @param id
     * @return
     */
    @GetMapping(value = "/{id}/download")
    public void download(HttpServletResponse response, HttpServletRequest request, @PathVariable Long id) throws IOException {
        documentService.download(response, request, id);
    }

    /**
     * 批量下载文件
     * @param ids
     * @return
     */
    @GetMapping(value = "/download")
    public void download(HttpServletResponse response, HttpServletRequest request, @RequestParam(name = "id") Long[] ids) throws IOException {
        documentService.download(response, request, ids);
    }

    /**
     * 预览文件
     * @return
     * @throws IOException
     */
    @GetMapping(value = "/{id}/preview")
    public void view(HttpServletResponse response, HttpServletRequest request, @PathVariable Long id) throws IOException {
        documentService.view(response, request, id);
    }


    /**
     * 移动文件
     * @param id
     * @return
     */
    @PostMapping("/{id}/{parentId}/move")
    public Result move(@PathVariable Long id, @PathVariable Long parentId) throws IOException {
        documentService.move(id, parentId);
        return ResultUtils.success();
    }

    /**
     * 复制文件
     * @param id
     * @return
     */
    @PostMapping("/{id}/{parentId}/copy")
    public Result copy(@PathVariable Long id, @PathVariable Long parentId) throws IOException {
        documentService.copy(id, parentId);
        return ResultUtils.success();
    }

}
