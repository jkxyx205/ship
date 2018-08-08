package com.yodean.oa.module.pan.service;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.rick.db.service.JdbcService;
import com.yodean.oa.common.core.service.BaseService;
import com.yodean.oa.common.exception.OAException;
import com.yodean.oa.common.plugin.document.entity.Document;
import com.yodean.oa.common.plugin.document.enums.ExceptionCode;
import com.yodean.oa.common.plugin.document.enums.FileType;
import com.yodean.oa.common.plugin.document.service.DocumentService;
import com.yodean.oa.module.pan.dto.PanAuthorityDTO;
import com.yodean.oa.module.pan.entity.PanAuthority;
import com.yodean.oa.module.pan.repository.PanAuthorityRepository;
import com.yodean.platform.domain.UserUtils;
import org.assertj.core.util.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * All rights Reserved, Designed By www.xhope.top
 *
 * @version V1.0
 * @Description: 权限权限管理
 * @author: Rick.Xu
 * @date: 7/26/18 12:22
 * @Copyright: 2018 www.yodean.com. All rights reserved.
 */
@Service
public class PanAuthorityService extends BaseService<PanAuthority> {

    public static final Logger logger = LoggerFactory.getLogger(PanAuthorityService.class);

    @Autowired
    private PanAuthorityRepository panAuthorityRepository;

    @Autowired
    private DocumentService documentService;

    @Autowired
    private JdbcService jdbcService;



    private static final String AUTHORITY_SQL = "select document_id documentId, inherit, permission_type  permissionType, employee_id employeeId from oa_pan_authority where document_id = :id and inherit = :inherit";

    private static final String AUTHORITY_USERS = "select DISTINCT employee_id from oa_pan_authority where document_id = :docId";

    private static final String AUTHORITY_CD_DELETE_SQL= "delete from oa_pan_authority where employee_id in(:empIds) and document_id in(:docIds) and permission_type = 'CD'";

    @Override
    protected JpaRepository<PanAuthority, Long> autowired() {
        return panAuthorityRepository;
    }

    /**
     * 新建文件夹
     * @param fileFullName
     * @param parentId
     * @param categoryId
     * @return
     */
    @Transactional
    public Document mkdir(String fileFullName, Long parentId, Long categoryId) {
        Document document = documentService.mkdir(fileFullName, parentId, categoryId);

        //设置权限
        authorize(wrapPanAuthorityDTO(parentId, document.getId()));

        return document;
    }

    /**
     * 文件上传
     * @param files
     * @param uploadPath
     * @param parentId
     * @param categoryId
     * @return
     */
    @Transactional
    public Long[] upload(List<MultipartFile> files, String uploadPath, Long parentId, Long categoryId) {
        try {
            List<Document> documents = documentService.upload(files, uploadPath, parentId, categoryId);

            Long[] ids = new Long[documents.size()];

            for (int i = 0; i < documents.size(); i++) {
                Long id = documents.get(i).getId();
                //设置权限
                authorize(wrapPanAuthorityDTO(parentId, id));
                ids[i] = id;
            }
            return ids;
        } catch (IOException e) {
            throw new OAException(ExceptionCode.FILE_UPLOAD_ERROR, e);
        }
    }

    /**
     * 移动文件
     * @param id
     * @param parentId
     */
    @Transactional
    public void move(long id, long parentId) {
        documentService.move(id, parentId);

        //设置权限
        removeInheritAuthority(id);
        authorize(wrapPanAuthorityDTO(parentId, id));
    }

    /**
     * 复制
     * @param id
     * @param parentId
     */
    @Transactional
    public void copy(long id, long parentId) {
        long newId = documentService.copy(id, parentId);
        //设置权限
        removeInheritAuthority(id);
        authorize(wrapPanAuthorityDTO(parentId, newId));
    }

    /**
     * 移除继承权限
     * @param id
     */
    private void removeInheritAuthority(Long id) {
        Set<PanAuthority> removeInheritSet = Sets.newHashSet();

        List<Document> documents = documentService.findAllSubDocument(id);

        documents.forEach(document -> removeInheritSet.addAll(panAuthorityRepository.findByDocumentIdAndInherit(document.getId(), true)));

        deleteAll(removeInheritSet);
    }

    /**
     * 设置继承权限
     * @param parentId
     * @param id
     * @return
     */
    private PanAuthorityDTO wrapPanAuthorityDTO(Long parentId, Long id) {
        PanAuthorityDTO panAuthorityDTO = new PanAuthorityDTO();
        panAuthorityDTO.setInherit(true);
        panAuthorityDTO.setDocumentId(id);

        Set<PanAuthority> parentPanAuthorities = findInheritAuthorityByDocumentId(parentId);

        panAuthorityDTO.setSet(extractAuthority(parentPanAuthorities, id));

        return panAuthorityDTO;
    }

    private PanAuthorityDTO wrapPanAuthorityDTO(Set<PanAuthority> panAuthorities, Long id) {
        PanAuthorityDTO panAuthorityDTO = new PanAuthorityDTO();
        panAuthorityDTO.setInherit(true);
        panAuthorityDTO.setDocumentId(id);
        panAuthorityDTO.setSet(panAuthorities);
        return panAuthorityDTO;
    }


    /**
     * 提取继承权限
     * @param parentPanAuthorities
     * @param id
     * @return
     */
    private Set<PanAuthority> extractAuthority(Set<PanAuthority> parentPanAuthorities, Long id) {

        Set<PanAuthority> panAuthorities = Sets.newHashSetWithExpectedSize(parentPanAuthorities.size());

        parentPanAuthorities.forEach(panAuthority -> {
            PanAuthority _panAuthority = new PanAuthority();
            _panAuthority.setInherit(true);
            _panAuthority.setDocumentId(id);
            _panAuthority.setEmployeeId(panAuthority.getEmployeeId());
            _panAuthority.setPermissionType(panAuthority.getPermissionType());
            panAuthorities.add(_panAuthority);
        });

        return panAuthorities;
    }


    /**
     *  授权
     * @param panAuthorityDTO 授权信息
     */
    @Transactional
    public void authorize(PanAuthorityDTO panAuthorityDTO) {
        //参数Params
        Set<PanAuthority> paramsPanAuthority = panAuthorityDTO.toSetEntity();

        //授权的文件
        Document document = documentService.findById(panAuthorityDTO.getDocumentId());
        //授权的文件授权信息
        List<PanAuthority> currentPanAuthority = panAuthorityRepository.findByDocumentId(document.getId());

        //----------------
        //持久化的权限信息
        Set<PanAuthority> parentPanAuthority = Sets.newHashSet();

        //持久化的权限信息
        Set<PanAuthority> dbPanAuthority = Sets.newHashSet();
        //保存期望的权限信息
        Set<PanAuthority> expectPanAuthority = Sets.newHashSet();

        dbPanAuthority.addAll(currentPanAuthority);

        currentPanAuthority.forEach(panAuthority -> {
            if (panAuthority.getInherit()) { //继承权限
                parentPanAuthority.add(panAuthority);
                expectPanAuthority.add(panAuthority);
            } else if (paramsPanAuthority.contains(panAuthority)) { //保留的非继承权限
                expectPanAuthority.add(panAuthority);
            }
        });

        expectPanAuthority.addAll(paramsPanAuthority);

        //授权递归处理
        getAuthorizeList(document, dbPanAuthority, expectPanAuthority, parentPanAuthority, true);

        //持久化处理
        Set<PanAuthority> removePanAuthority = Sets.newHashSet(dbPanAuthority);
        removePanAuthority.removeAll(expectPanAuthority);

        saveAll(expectPanAuthority);
        deleteAll(removePanAuthority);

        //构建CD权限
        buildCdAuthority(panAuthorityDTO.getDocumentId());

    }

    /**
     * 根据id权限
     * @param id
     * @return
     */
    private Set<PanAuthority> findInheritAuthorityByDocumentId(Long id, Boolean inherit) {
        Map<String, Object> params = Maps.newHashMapWithExpectedSize(2);
        params.put("id", id);

        if (Objects.nonNull(inherit)) {
            params.put("inherit", inherit ? "1" : "0");
        }

        return Sets.newHashSet(jdbcService.query(AUTHORITY_SQL, params, PanAuthority.class));
    }

    private Set<PanAuthority> findInheritAuthorityByDocumentId(Long id) {
       return findInheritAuthorityByDocumentId(id, null);
    }


    /**
     * 递归授权
     * @param document
     * @param dbPanAuthority
     * @param expectPanAuthority
     * @param parentPanAuthority
     */
    private void getAuthorizeList(Document document, Set<PanAuthority> dbPanAuthority, Set<PanAuthority> expectPanAuthority, Set<PanAuthority> parentPanAuthority, boolean first) {

        if (!first && !document.getInherit()) { //终止 非继承
            return;
        }

        Set<PanAuthority> thisExpectPanAuthority = Sets.newHashSet();

        if(first) { //授权目录
            thisExpectPanAuthority.addAll(expectPanAuthority);
        } else  { //子目录
            List<PanAuthority> currentPanAuthority = panAuthorityRepository.findByDocumentId(document.getId());

            dbPanAuthority.addAll(currentPanAuthority);

            currentPanAuthority.forEach(panAuthority -> {
                if (!panAuthority.getInherit()) { //非继承权限
                    thisExpectPanAuthority.add(panAuthority);

                } else if (parentPanAuthority.contains(panAuthority) && PanAuthority.PermissionType.CD != panAuthority.getPermissionType()) { //继承权限
                    thisExpectPanAuthority.add(panAuthority);

                }
            });

            thisExpectPanAuthority.addAll(parentPanAuthority);

            expectPanAuthority.addAll(thisExpectPanAuthority);
        }
//
        if (FileType.FOLDER == document.getFileType()) {
            documentService.findSubDocument(document.getId()).forEach(subDocument -> {
                getAuthorizeList(subDocument, dbPanAuthority, expectPanAuthority,
                        extractAuthority(thisExpectPanAuthority, subDocument.getId()),
                        false);
            });
        }

    }

    /**
     * 禁用继承权限
     */
    @Transactional
    public void disableInherit(Long id) {

        List<PanAuthority> panAuthorities = panAuthorityRepository.findByDocumentId(id);

        Set<PanAuthority> uniquePanAuthorities = Sets.newHashSetWithExpectedSize(panAuthorities.size());

        Set<PanAuthority> removePanAuthorities = Sets.newHashSetWithExpectedSize(panAuthorities.size());
        panAuthorities.forEach(panAuthority -> {

            if (panAuthority.getInherit()) {//继承的
                PanAuthority _panAuthority = new PanAuthority();
                _panAuthority.setInherit(false);
                _panAuthority.setDocumentId(id);
                _panAuthority.setPermissionType(panAuthority.getPermissionType());
                _panAuthority.setEmployeeId(panAuthority.getEmployeeId());

                if (panAuthorities.contains(_panAuthority)) {
                    removePanAuthorities.add(panAuthority);
                } else {
                    panAuthority.setInherit(false);
                    uniquePanAuthorities.add(panAuthority);
                }

            } else {
                uniquePanAuthorities.add(panAuthority);
            }
        });

        deleteAll(removePanAuthorities); //删除重复的
        saveAll(uniquePanAuthorities);   //保存非继承状态

        Document document = documentService.findById(id);
        document.setInherit(false);
        documentService.save(document);

    }

    /**
     * 启用继承权限
     */
    @Transactional
    public void enableInherit(Long id) {
        //获取继承权限
        Document curDocument = documentService.findById(id);


        PanAuthorityDTO panAuthorityDTO = wrapPanAuthorityDTO(curDocument.getParentId(), id);

        Set<PanAuthority> inheritPanAuthorities = panAuthorityDTO.toSetEntity();

        //当前自身权限
        List<PanAuthority> selfPanAuthorities = panAuthorityRepository.findByDocumentId(curDocument.getId());

        Set<PanAuthority> addPanAuthorities = Sets.newHashSetWithExpectedSize(inheritPanAuthorities.size());

        selfPanAuthorities.forEach(panAuthority -> {

            panAuthority.setInherit(true);

            if (!inheritPanAuthorities.contains(panAuthority)) { //非继承权限
                panAuthority.setInherit(false);
            }
        });

        //新增权限
        panAuthorityDTO.toSetEntity().forEach(panAuthority -> {
            if (!selfPanAuthorities.contains(panAuthority)) { //包含继承权限
                PanAuthority _panAuthority = new PanAuthority();
                _panAuthority.setInherit(true);
                _panAuthority.setDocumentId(id);
                _panAuthority.setPermissionType(panAuthority.getPermissionType());
                _panAuthority.setEmployeeId(panAuthority.getEmployeeId());
                addPanAuthorities.add(_panAuthority);
            }

        });

        selfPanAuthorities.addAll(addPanAuthorities);

        //保存文件
        curDocument.setInherit(true);
        documentService.save(curDocument);

        //子目录授权
        authorize(wrapPanAuthorityDTO(Sets.newHashSet(selfPanAuthorities), curDocument.getId()));

    }

    /**
     * 构建CD路径权限
     * @param id
     */
    @Transactional
    public void buildCdAuthority(Long id) {
        Map<String, Object> params = Maps.newHashMapWithExpectedSize(1);
        params.put("docId", id);
        List<Long> empIdList = jdbcService.query(AUTHORITY_USERS, params, Long.class);

        List<Document> pathDocuments = Lists.newArrayList(documentService.findAllSubDocument(id));
        pathDocuments.addAll(documentService.findParentsDocuments2(id));

        List<Long> docIds = Lists.newArrayList();

        Set<PanAuthority> cdPanAuthority = Sets.newHashSet();

        for(Document doc : pathDocuments) {
            docIds.add(doc.getId());

            for (Long empId : empIdList) {
                PanAuthority panAuthority = new PanAuthority();
                panAuthority.setDocumentId(doc.getId());
                panAuthority.setEmployeeId(empId);
                panAuthority.setInherit(true);
                panAuthority.setPermissionType(PanAuthority.PermissionType.CD);
                cdPanAuthority.add(panAuthority);
            }
        }

        Map<String, List<?>> params2 = Maps.newHashMapWithExpectedSize(2);
        params2.put("empIds", empIdList);
        params2.put("docIds", docIds);

        //delete
        jdbcService.getNamedJdbcTemplate().update(AUTHORITY_CD_DELETE_SQL, params2);

        //save
        saveAll(cdPanAuthority);
    }

    /**
     * 逻辑删除文件
     * @param id
     */
    public void delete(Long id) {
        documentService.deleteByFlag(id);
    }

    /**
     * 逻辑彻底删除
     * @param id
     */
    public void clean(Long id) {
        documentService.clean(id);
    }

    /**
     * 重命名
     * @param id
     * @param name
     */
    public void rename(Long id, String name) {
        documentService.rename(id, name);
    }

    /**
     * 还原文件
     * @param id
     */
    public void putBack(Long id) {
        documentService.putBack(id);
    }

    /**
     * 下载
     * @param ids
     */
    public void download(HttpServletRequest request, HttpServletResponse response, Long ... ids) throws IOException {
        documentService.download(request, response, ids);
    }


    /**
     * 预览
     * @param id
     */
    public void view(HttpServletRequest request, HttpServletResponse response, Long id) throws IOException {
        documentService.view(request, response, id);
    }


    /**
     * 权限检查
     * @param id 文件id
     * @param permissionType 权限
     * @return
     */
    public boolean validate(Long id, PanAuthority.PermissionType permissionType) {
        return panAuthorityRepository.countByDocumentIdAndEmployeeIdAndPermissionType(id, UserUtils.getCurrentEmployee().getId(), permissionType) > 0 ? true : false;
    }
}