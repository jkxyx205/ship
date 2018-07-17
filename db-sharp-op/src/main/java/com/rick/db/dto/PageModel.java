package com.rick.db.dto;

import java.util.Map;

/**
 * Created by Rick.Xu on 2016/03/22.
 */
public class PageModel {
    private int page; //当前页

    private int rows = -1;    //每页显示行数,rows == -1 一次性全部加载出来,不再分页


    private String sidx; //排序字段

    private String sord; //排序方式

    public PageModel() {

    }

    private Map<String,String> dicMap;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public String getSidx() {
        return sidx;
    }

    public void setSidx(String sidx) {
        this.sidx = sidx;
    }

    public String getSord() {
        return sord;
    }

    public void setSord(String sord) {
        this.sord = sord;
    }

}
