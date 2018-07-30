package com.yodean.oa.module.pan.service;

import com.yodean.oa.module.pan.dto.PanAuthorityDTO;
import com.yodean.oa.module.pan.entity.PanAuthority;
import org.assertj.core.util.Sets;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Set;

/**
 * All rights Reserved, Designed By www.xhope.top
 *
 * @version V1.0
 * @Description: (用一句话描述该文件做什么)
 * @author: Rick.Xu
 * @date: 7/26/18 16:39
 * @Copyright: 2018 www.yodean.com. All rights reserved.
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class PanAuthorityServiceTest {
    @Autowired
    private PanAuthorityService panAuthorityService;

    @Test
    public void authorize() throws Exception {
        PanAuthorityDTO panAuthorityDTO = new PanAuthorityDTO();
        panAuthorityDTO.setDocumentId(153258889857707L);
        panAuthorityDTO.setInherit(true);

        PanAuthority a1 = new PanAuthority();
        a1.setEmployeeId(153190447547181L);
        a1.setPermissionType(PanAuthority.PermissionType.READ);

        PanAuthority a2 = new PanAuthority();
        a2.setEmployeeId(153190447547181L);
        a2.setPermissionType(PanAuthority.PermissionType.UPLOAD);

        Set<PanAuthority> set = Sets.newLinkedHashSet(a1,a2);

        panAuthorityDTO.setSet(set);




        panAuthorityService.authorize(panAuthorityDTO);

    }

    @Test
    public void testContains() {
        PanAuthority a1 = new PanAuthority();
        a1.setDocumentId(1L);
        a1.setEmployeeId(153190447547181L);
        a1.setPermissionType(PanAuthority.PermissionType.READ);
        a1.setInherit(true);

        PanAuthority a2 = new PanAuthority();
        a2.setDocumentId(1L);
        a2.setEmployeeId(153190447547181L);
        a2.setPermissionType(PanAuthority.PermissionType.UPLOAD);
        a2.setInherit(true);

        PanAuthority a3 = new PanAuthority();
        a3.setDocumentId(1L);
        a3.setEmployeeId(153190447547181L);
        a3.setPermissionType(PanAuthority.PermissionType.UPLOAD);
        a3.setInherit(true);

        Set<PanAuthority> set = Sets.newHashSet();
        set.add(a1);
        set.add(a2);

        Set<PanAuthority> set2 = Sets.newHashSet();
        set2.add(a3);
        set2.addAll(set);

        System.out.println(set2.size());
    }


    @Test
    public void testValidate() {
        boolean result = panAuthorityService.validate(153268291887097L, PanAuthority.PermissionType.UPLOAD);
        boolean result2 = panAuthorityService.validate(153268291887097L, PanAuthority.PermissionType.DELETE);
        Assert.assertTrue(result);
        Assert.assertFalse(result2);
    }

    @Test
    public void testDisableInherit() {
        panAuthorityService.disableInherit(153268302793603L);
    }

    @Test
    public void testEnableInherit() {
        panAuthorityService.enableInherit(153268302793603L);
    }

    @Test
    public void testBuildCdAuthority() {
        panAuthorityService.buildCdAuthority(153270039008161L);
    }
}