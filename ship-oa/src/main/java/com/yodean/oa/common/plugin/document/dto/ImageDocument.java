package com.yodean.oa.common.plugin.document.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.yodean.oa.common.Global;
import com.yodean.oa.common.plugin.document.entity.Document;

import java.io.File;

/**
 * Created by rick on 2018/3/22.
 */
public class ImageDocument extends Document {


//    private Document document;

    public ImageDocument() {}

    public ImageDocument(Document document) {
//        this.document = document;
        setName(document.getName());
        setContentType(document.getContentType());
        setPath(document.getPath());
        setExt(document.getExt());
        setUpdateBy(document.getUpdateBy());
        setUpdateDate(document.getUpdateDate());
        setCreateBy(document.getCreateBy());
        setCreateDate(document.getCreateDate());
    }

    /***
     *  原图
     */
    private String source;

    /***
     *  原图压缩图中图
     */
    private String thumbnail;

    /***
     *  原图压缩图小图
     */
    private String thumbnailSmall;


    public String getUrlThumbnailPath() {
        return Global.CDN + "/" + getPath() + "-thumbnail." + getExt();
    }

    public String getUrlThumbnailSmallPath() {

        return Global.CDN + "/" + getPath() + "-thumbnail-small." + getExt();
    }

    public String getUrlThumbnailSmallPath1x1() {
        return Global.CDN + "/" + getPath() + "-thumbnail-small-1x1." + getExt();
    }

//    public String getFileThumbnailPath() {
//        return rootPath + File.separator + getPath() + "-thumbnail." + getExt();
//    }
//
//    public String getFileThumbnailSmallPath() {
//        return rootPath + File.separator + getPath() + "-thumbnail-small." + getExt();
//    }

    @JsonIgnore
    public String getFileAbsouteThumbnailPath() {
        return Global.DOCUMENT + File.separator + getPath() + "-thumbnail." + getExt();
    }

    @JsonIgnore
    public String getFileAbsouteThumbnailSmallPath() {
        return Global.DOCUMENT  + File.separator + getPath()  + "-thumbnail-small."  + getExt();
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getThumbnailSmall() {
        return thumbnailSmall;
    }

    public void setThumbnailSmall(String thumbnailSmall) {
        this.thumbnailSmall = thumbnailSmall;
    }
}
