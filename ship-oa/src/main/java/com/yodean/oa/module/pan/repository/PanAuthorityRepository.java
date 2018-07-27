package com.yodean.oa.module.pan.repository;

import com.yodean.oa.module.pan.entity.PanAuthority;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * All rights Reserved, Designed By www.xhope.top
 *
 * @version V1.0
 * @Description: (用一句话描述该文件做什么)
 * @author: Rick.Xu
 * @date: 7/26/18 12:21
 * @Copyright: 2018 www.yodean.com. All rights reserved.
 */
public interface PanAuthorityRepository extends JpaRepository<PanAuthority, Long> {
    /**
     * 查找权限
     * @param id 文档id
     * @param inherit 是否继承
     * @return
     */
    List<PanAuthority> findByDocumentIdAndInherit(Long id, Boolean inherit);

    List<PanAuthority> findByDocumentId(Long id);

    long countByDocumentIdAndEmployeeIdAndPermissionType(Long documentId, Long employeeId, PanAuthority.PermissionType permissionType);
}
