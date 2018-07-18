package com.yodean.oa.common.plugin.document.service;


import com.yodean.common.enums.DelFlag;
import com.yodean.common.util.MimeTypesUtils;
import com.yodean.common.util.ZipUtils;
import com.yodean.oa.common.Global;
import com.yodean.oa.common.exception.OAException;
import com.yodean.oa.common.plugin.document.dao.DocumentRepository;
import com.yodean.oa.common.plugin.document.dto.ImageDocument;
import com.yodean.oa.common.plugin.document.entity.Document;
import com.yodean.oa.common.plugin.document.enums.DocumentCategory;
import com.yodean.oa.common.plugin.document.enums.ExceptionCode;
import com.yodean.oa.common.plugin.document.enums.FileType;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.Validate;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Example;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.zip.ZipOutputStream;

/**
 * Created by rick on 2018/3/22.
 */
@Service
public class DocumentService {

    @Resource
    private DocumentRepository documentRepository;

    @Resource
    private DocumentHandler documentHandler;

    @Resource
    private ImageHandler imageHandler;

    @Resource
    private JdbcTemplate jdbcTemplate;

    private static final String READ_ATTACHMENT = "attachment";

    private static final String READ_INLINE = "inline";

    /**
     *
     * @param file 文件
     * @param folder 文件存储路径
     * @param parentId 所属文件夹
     * @param category 分类
     * @param id 分类id
     * @return
     * @throws IOException
     */
    @Transactional
    public Document upload(MultipartFile file, String folder, Long parentId, DocumentCategory category, Long id) throws IOException {
        //上传到服务器
        Document document = documentHandler.store(folder, file);
        document.setCategory(category);
        document.setCategoryId(id);
        document.setParentId(parentId);
        document.setInherit(true);
        document.setFullName(getUniqueName(parentId, FileType.FILE, document.getFullName())); //如果重名自动编号
        return save(document);
    }

    @Transactional
    public List<Document> upload(List<MultipartFile> files, String folder, Long parentId, DocumentCategory category, Long id) throws IOException {
        List<Document> documents = new ArrayList<>(files.size());
        for(MultipartFile file : files) {
            documents.add(upload(file, folder, parentId, category, id));
        }
        return documents;
    }

    /***
     * 自动裁剪
     * @param folder
     * @param id
     * @param aspectRatioW
     * @param aspectRatioH
     * @return
     * @throws IOException
     */
    public Document cropAuto(String folder, Long id, Integer aspectRatioW, Integer aspectRatioH) throws IOException {
        Document document = findById(id);
        ImageDocument image = new ImageDocument();
        BeanUtils.copyProperties(document, image);

        return imageHandler.cropPic(folder, image, aspectRatioW, aspectRatioH);
    }


    /***
     * 手动裁剪
      * @param folder
     * @param id
     * @param x
     * @param y
     * @param w
     * @param h
     * @param aspectRatioW
     * @param aspectRatioH
     * @return
     * @throws IOException
     */
    public Document cropManual(String folder, Long id,
                         Integer x, Integer y, Integer w, Integer h,
                         Integer aspectRatioW, Integer aspectRatioH) throws IOException {
        Document document = findById(id);
        ImageDocument image = new ImageDocument();
        BeanUtils.copyProperties(document, image);

        return imageHandler.cropPic(folder, image, x, y, w, h, aspectRatioW, aspectRatioH);

    }

    public Document save(Document document) {
        return documentRepository.save(document);
    }

    public List<Document> save(Iterable<Document> iterator) {
        return documentRepository.saveAll(iterator);
    }

    public Document findById(Long id) {
        Optional<Document> optional = documentRepository.findById(id);
        if (optional.isPresent()) {
            return optional.get();
        }

        throw new RuntimeException("文件不存在");
    }

    /***
     * 逻辑删除文件
     * @param id
     */
    public void delete(Long id) {
        Document document = findById(id);
        document.setDelFlag(DelFlag.DEL_FLAG_REMOVE);
        save(document);
    }

    /***
     * 逻辑彻底删除文件
     * @param id
     */
    public void clean(Long id) {
        Document document = findById(id);
        document.setDelFlag(DelFlag.DEL_FLAG_CLEAN);
        save(document);
    }



    /***
     * 绑定附件到实例上
     * @param docIds
     * @param categoryId
     */
    public void update(Set<Long> docIds, DocumentCategory category, Long categoryId) {
        Validate.notNull(categoryId);
        Validate.notNull(docIds);

        if (docIds.size() == 0) {
            return;
        }

        String sql = "UPDATE oa_document set category = ?, category_id = ? WHERE id = ?";

        List<Object[]> params = new ArrayList<>(docIds.size());
        for (Long docId : docIds) {
            params.add(new Object[]{category.name(), categoryId, docId});
        }

        jdbcTemplate.batchUpdate(sql, params);
    }

    /**
     * 查找实例的所有文件
     * @param category
     * @param categoryId
     * @return
     */
    public List<Document> findById(DocumentCategory category, Long categoryId) {
        Document document = new Document();
        document.setCategory(category);
        document.setCategoryId(categoryId);
        document.setDelFlag(DelFlag.DEL_FLAG_NORMAL);

        Example example = Example.of(document);
        return documentRepository.findAll(example);
    }

    /**
     * 查看所有父目录
     * @param id
     * @return
     */
    public List<Document> findParentsDocument(Long id) {
        return findDocumentPath(id, false);
    }

    /**
     * 查看文件路径
     * @param id
     * @return
     */
    public List<Document> findDocumentPath(Long id) {
        return findDocumentPath(id, true);
    }

    /**
     * 查看文件路径
     * @param id
     * @return
     */
    private List<Document> findDocumentPath(Long id, boolean includeSelf) {
        Document document =  findById(id);

        List<Document> parentsDocument = new ArrayList<>();

        Document curDocument = document;

        while (curDocument.getParentId() != null) {
            Document parent = findById(curDocument.getParentId());
            parentsDocument.add(parent);
            curDocument = parent;
        }

        Collections.reverse(parentsDocument);

        if (includeSelf)
            parentsDocument.add(document);

        return parentsDocument;

    }

    /**
     * 新增加文件，检查文件是否重复
     * @param parentId 父目录
     * @param fileFullName 新的名称
     * @return
     */
    public boolean isUniqueFileNameWithNew(Long parentId, FileType fileType, String fileFullName) {
        List<Document> siblingsList = findSubDocument(parentId);

        for(Document document : siblingsList) {
            if(document.getFileType() == fileType && document.getFullName().equals(fileFullName)) {
                 return false;
            }
        }

        return true;
    }

    /**
     * 已存在文件，检查文件是否重复
     * @param docId
     * @param fileFullName
     * @return
     */
    public boolean isUniqueFileNameWithExists(Long docId, FileType fileType, String fileFullName) {
        Long parentId = findById(docId).getParentId();

        List<Document> siblingsList = findSubDocument(parentId);

        for(Document document : siblingsList) {
            if(document.getFileType() == fileType && docId != document.getId() && document.getFullName().equals(fileFullName)) {
                return false;
            }
        }
        return true;
    }

    /**
     * 根据文件名查找目录下的文件
     * @param parentId
     * @param fileType
     * @param fileFullName
     * @return
     */
    public Document findByNameFromParent(Long parentId, FileType fileType, String fileFullName) {
        Document document = new Document();
        document.setFullName(fileFullName);
        document.setParentId(parentId);
        document.setFileType(fileType);
        document.setDelFlag(DelFlag.DEL_FLAG_NORMAL);

        Example<Document> example = Example.of(document);
        return documentRepository.findOne(example).get();
    }


    /**
     * 获取目录下的唯一名称
     * @return
     */
    public String getUniqueName(Long parentId, FileType fileType, String fileFullName) {
        List<Document> siblingsList = findSubDocument(parentId);

        List<String> names = new ArrayList<>(siblingsList.size());

        for(Document document : siblingsList) {
            if (fileType == document.getFileType())
                names.add(document.getFullName());
        }

        String name = StringUtils.stripFilenameExtension(fileFullName);
        String ext = StringUtils.getFilenameExtension(fileFullName);

        int sno = 1;
        while(names.contains(fileFullName)) {
            fileFullName = name + "("+sno+")";
            if (!StringUtils.isEmpty(ext)) {
                fileFullName =  fileFullName + "." + ext;
            }
            sno++;
        }

        return fileFullName;
    }

    /**
     * 查找所有子文件（夹）
     * @return
     */
    public List<Document> findSubDocument(Long parentId) {
        Document document = new Document();
        document.setParentId(parentId);
        document.setDelFlag(DelFlag.DEL_FLAG_NORMAL);

        Example example = Example.of(document);
        return documentRepository.findAll(example);
    }

    /**
     * 文件下载
     * @param response
     * @param request
     * @param ids
     * @throws IOException
     */
    public void download(HttpServletResponse response, HttpServletRequest request, Long ... ids) throws IOException {
        Validate.noNullElements(ids);

        Document document;

        if (ids.length > 1) { //批量下载
            document = new Document();
            document.setFileType(FileType.FOLDER);

        } else {
            document = findById(ids[0]);
        }

        OutputStream os;

        if (document.getFileType() == FileType.FOLDER) { //压缩下载
            //1. 检查是否有下载的权限 //TODO

            //2.子文件
            List<Document> subDocuments;
            if (ids.length > 1) { //批量下载
                subDocuments = new ArrayList<>(ids.length);
                for (Long id : ids) {
                    subDocuments.add(findById(id));
                }
                document.setFullName(subDocuments.get(0).getFullName() + "等" + ids.length + "个文件");

            } else { //下载单个文件夹
                subDocuments = findSubDocument(document.getId());
            }

//           Path path = Files.createTempDirectory( Paths.get(doPrivileged(new GetPropertyAction("java.io.tmpdir"))),null);

            Path path = Files.createTempDirectory( Paths.get(Global.TMP),null);

            File _home = path.toFile();

            File root = new File(_home, document.getFullName());
            root.mkdir();

            //4.创建
            downloadFolder(root, subDocuments);

            String zipName = document.getName() + ".zip";
            File zipFile = new File(_home, zipName);

            ZipOutputStream zipOut = new ZipOutputStream(new FileOutputStream(zipFile));

            ZipUtils.zipDirectoryToZipFile(_home.getAbsolutePath(), root, zipOut);

            os = getResponseOutputStream(response, request, zipName, READ_ATTACHMENT);

            zipOut.close();

            FileCopyUtils.copy(new FileInputStream(zipFile), os);

            Runtime.getRuntime().addShutdownHook(new Thread(() -> FileUtils.deleteQuietly(_home)));
//           FileUtils.forceDeleteOnExit(_home);
        } else { //单文件下载
            os = getResponseOutputStream(response, request, document.getFullName(), READ_ATTACHMENT);
            FileCopyUtils.copy(new FileInputStream(document.getFileAbsolutePath()), os);
        }

        os.close();
    }

    private void downloadFolder(File folder, List<Document> subDocuments) throws IOException {
        for (Document subDocument :  subDocuments) {
            if (subDocument.getFileType() == FileType.FOLDER) {//文件夹
                File subFolder = new File(folder, subDocument.getName());
                subFolder.mkdir();
                downloadFolder(subFolder, findSubDocument(subDocument.getId()));
            } else { //文件
                FileCopyUtils.copy(new FileInputStream(subDocument.getFileAbsolutePath()), new FileOutputStream(new File(folder, subDocument.getFullName())));
            }
        }
    }

    /**
     * 文件预览
     * @param response
     * @param request
     * @param id
     * @throws IOException
     */
    public void view(HttpServletResponse response, HttpServletRequest request, Long id) throws IOException {
        Document document = findById(id);
        OutputStream os = getResponseOutputStream(response, request, document.getFullName(), READ_INLINE);
        if (document.getFileType() == FileType.FOLDER) {
            throw new OAException(ExceptionCode.FILE_DOWNLOAD_ERROR);
        } else {
            FileCopyUtils.copy(new FileInputStream(document.getFileAbsolutePath()), os);
        }
    }

    private static OutputStream getResponseOutputStream(HttpServletResponse response, HttpServletRequest request, String fileName, String readType) throws IOException {
        String _fileName = fileName.replaceAll("[/:*?\"<>[|]]", "");

        String browserType = request.getHeader("User-Agent").toLowerCase();

        if(browserType.indexOf("firefox") > -1) { //FF
            _fileName = "=?"+ StandardCharsets.UTF_8+"?B?"+(new String(Base64.encodeBase64(_fileName.getBytes(StandardCharsets.UTF_8))))+"?=";
        } else {
            if(fileName.matches(".*[^\\x00-\\xff]+.*")) {
                if(request.getHeader("User-Agent").toLowerCase().indexOf("msie") > -1) { //IE
                    _fileName = java.net.URLEncoder.encode(_fileName,StandardCharsets.UTF_8.name());
                } else  { //其他
                    _fileName = new String(_fileName.getBytes(StandardCharsets.UTF_8), "ISO-8859-1");
                }
            }
        }

        response.reset();// 清空输出流
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        response.setHeader("Content-disposition", ""+readType+"; filename="+_fileName+"");// 设定输出文件头
        response.setContentType(MimeTypesUtils.getContentType(_fileName));// 定义输出类型
        OutputStream os = response.getOutputStream(); // 取得输出流
        return os;
    }


    /**
     * 新建文件夹
     * @param fileFullName
     * @param parentId
     * @param documentCategory
     * @param id
     */
    public Long mkdir(String fileFullName, Long parentId, DocumentCategory documentCategory, Long id) {
        //检查文件名是否重复
        if (!isUniqueFileNameWithNew(parentId, FileType.FOLDER, fileFullName)) {
            throw new OAException(ExceptionCode.FILE_NAME_DUPLICATE_ERROR);
        }

        Document document = new Document();
        document.setCategory(documentCategory);
        document.setCategoryId(id);
        document.setFileType(FileType.FOLDER);
        document.setFullName(fileFullName);
        document.setParentId(parentId);
        document.setInherit(true);// 初始化启用继承
        save(document);
        return document.getId();
    }

    public void rename(Long id, String name) {
        Document document = findById(id);
        //检查文件名是否重复
        if (!isUniqueFileNameWithExists(id, document.getFileType(), name)) {
            throw new OAException(ExceptionCode.FILE_NAME_DUPLICATE_ERROR);
        }

        document.setFullName(name);
        save(document);
    }
}