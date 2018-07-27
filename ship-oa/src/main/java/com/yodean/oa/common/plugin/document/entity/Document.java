package com.yodean.oa.common.plugin.document.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.yodean.oa.common.Global;
import com.yodean.oa.common.plugin.document.enums.FileType;
import com.yodean.platform.domain.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.util.StringUtils;

import javax.persistence.*;
import java.io.File;
import java.util.Objects;

/**
 * Created by rick on 2018/3/22.
 */
@Getter
@Setter
@Entity
@Table(name = "oa_document",
        uniqueConstraints = {@UniqueConstraint(columnNames={"category_id", "type", "del_flag","parent_id", "name", "ext"})})
@DynamicUpdate
public class Document extends BaseEntity {
    /**
     * 文件名称（不包括后缀）
     */
    private String name;

    /**
     * 物理路径
     */
    private String path;

    /**
     * 文件扩展名
     */
    private String ext;

    /**
     * 文件大小
     */
    private Long size;

    /**
     *  文件类型
     */
    @Column(name = "content_type")
    private String contentType;

    /**
     * 所属文件夹id
     */
    @Column(name = "parent_id")
    private Long parentId;

    /**
     *文件类型
     */
    @Enumerated(EnumType.ORDINAL)
    @Column(name = "type")
    private FileType fileType;


    /**
     * 附件所属的实例id
     */
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

    /**
     * 获取完整名称
     * @return
     */
    public String getFullName() {
        if (StringUtils.isEmpty(this.getExt()))
            return this.name;

        return this.name + (StringUtils.isEmpty(this.getExt()) ? "" : "." + this.getExt());
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
        return Global.CDN + "/" + path + (StringUtils.isEmpty(this.getExt()) ? "" : "." + this.getExt());
    }

    @Override
    public void initParams() {
        if (Objects.isNull(inherit)) {
            inherit = true; //默认实用继承权限
        }
    }
}
