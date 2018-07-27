package com.yodean.oa.module.pan.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.yodean.oa.common.plugin.document.entity.Document;
import com.yodean.oa.common.plugin.document.enums.FileType;
import com.yodean.platform.domain.BaseEntity;
import com.yodean.platform.domain.Company;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * All rights Reserved, Designed By www.xhope.top
 *
 * @version V1.0
 * @Description: 共享文件
 * @author: Rick.Xu
 * @date: 7/26/18 11:38
 * @Copyright: 2018 www.yodean.com. All rights reserved.
 */
@Getter
@Setter
@Entity
@Table(name = "oa_pan_share")
public class PanShare extends BaseEntity {
    /**
     * 所属公司
     */
    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;


    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "document_id")
    private Document document;

    @Transient
    @JsonIgnoreProperties
    private Long companyId;

    @Transient
    @JsonIgnoreProperties
    private String name;


    @Override
    public void initParams() {
        Company company = new Company();
        company.setId(companyId);

        Document document = new Document();
        document.setName(name);
        document.setCategoryId(this.getId());
        document.setFileType(FileType.FOLDER);
        document.setInherit(true);

        this.setCompany(company);
        this.setDocument(document);
    }
}