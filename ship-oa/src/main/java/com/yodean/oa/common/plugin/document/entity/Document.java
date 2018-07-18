package com.yodean.oa.common.plugin.document.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.yodean.common.domain.BaseEntity;


import com.yodean.oa.common.Global;
import com.yodean.oa.common.plugin.document.enums.DocumentCategory;
import com.yodean.oa.common.plugin.document.enums.FileType;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.util.StringUtils;

import javax.persistence.*;
import java.io.File;

/**
 * Created by rick on 2018/3/22.
 */
@Getter
@Setter
@Entity
@Table(name = "oa_document",
        uniqueConstraints = {@UniqueConstraint(columnNames={"category", "category_id", "type", "del_flag", "name", "ext"})})
@DynamicUpdate
public class Document extends BaseEntity {

    private String name;

    private String path;

    private String ext;

    private Long size;

    @Column(name = "content_type")
    private String contentType;

    @Column(name = "parent_id")
    private Long parentId;

    /**
     *文件类型
     */
    @Enumerated(EnumType.ORDINAL)
    @Column(name = "type")
    private FileType fileType;

    @Enumerated(EnumType.STRING)
    @Column(name = "category")
    private DocumentCategory category;

    @Column(name = "category_id")
    private Long categoryId;


    /**
     * 是否启用权限继承
     */
    private Boolean inherit;


    public void setFullName(String fullName) {
        String fileName = StringUtils.stripFilenameExtension(fullName);
        String fileExt = StringUtils.getFilenameExtension(fullName);
        setName(fileName);
        setExt(fileExt);
    }

    public String getFullName() {
        if (StringUtils.isEmpty(this.getExt()))
            return this.name;

        return this.name + "." + this.getExt();
    }

    /***
     * 获取原文件绝对路径
     * @return
     */
    @JsonIgnore
    public String getFileAbsolutePath() {
        if (FileType.FOLDER == this.fileType) return null;
        return Global.DOCUMENT + File.separator + path;
    }


    /***
     * 获取原文件网络地址
     * @return
     */
    public String getUrlPath() {
        if (FileType.FOLDER == this.fileType) return null;
        return Global.CDN + "/" + path + "." + ext;
    }
}
