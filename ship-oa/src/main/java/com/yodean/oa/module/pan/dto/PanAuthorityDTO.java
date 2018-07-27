package com.yodean.oa.module.pan.dto;

import com.google.common.collect.Sets;
import com.yodean.oa.module.pan.entity.PanAuthority;
import lombok.Data;
import org.apache.commons.collections.CollectionUtils;

import java.io.Serializable;
import java.util.Objects;
import java.util.Set;

/**
 * All rights Reserved, Designed By www.xhope.top
 *
 * @version V1.0
 * @Description: 授权参数
 * @author: Rick.Xu
 * @date: 7/26/18 15:18
 * @Copyright: 2018 www.yodean.com. All rights reserved.
 */
@Data
public class PanAuthorityDTO implements Serializable {

    private Long documentId;

    private Boolean inherit;

    private Set<PanAuthority> set;

    public Set<PanAuthority> toSetEntity() {

        if (CollectionUtils.isNotEmpty(set)) {
            set.forEach(panAuthority -> {
                panAuthority.setDocumentId(documentId);
                if (Objects.isNull(panAuthority.getInherit()))
                    panAuthority.setInherit(false); //非继承权限
            });
        }

        return Sets.newHashSet(set);
    }

}
