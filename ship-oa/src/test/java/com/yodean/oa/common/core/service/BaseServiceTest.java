package com.yodean.oa.common.core.service;

import com.yodean.oa.module.asset.seal.entity.Seal;
import com.yodean.oa.module.asset.seal.service.SealService;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * All rights Reserved, Designed By www.xhope.top
 *
 * @version V1.0
 * @Description: (用一句话描述该文件做什么)
 * @author: Rick.Xu
 * @date: 8/8/18 13:40
 * @Copyright: 2018 www.yodean.com. All rights reserved.
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class BaseServiceTest {
    @Autowired
    private SealService sealService;

    @Test
    public void findAll() {
        Seal seal = new Seal();
//        seal.setTitle("我对印章2");

        Page<Seal> page = sealService.findAll(seal, 0, 3, Sort.Direction.DESC, "createDate");
        System.out.println("---------------");
        System.out.println(ToStringBuilder.reflectionToString(page));
    }
}