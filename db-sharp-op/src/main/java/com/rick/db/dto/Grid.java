package com.rick.db.dto;

import java.util.List;

/**
 * Created by Rick.Xu on 2016/03/22.
 */
public class Grid<T> {

    /**
     * 当前页面索引
     */
    private int page;

    private int pageRows;

    /***
     * 总纪录数
     */
    private long records;

    /***
     * 总页数
     */
    private long total;

    /***
     * 纪录
     */
    private List<T> rows;

    public List<T> getRows() {
        return rows;
    }

    public void setRows(List<T> rows) {
        this.rows = rows;
    }

    public long getRecords() {
        return records;
    }

    public void setRecords(long records) {
        this.records = records;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPageRows() {
        return pageRows;
    }

    public void setPageRows(int pageRows) {
        this.pageRows = pageRows;
    }
}