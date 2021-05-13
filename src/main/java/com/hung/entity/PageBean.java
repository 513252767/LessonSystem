package com.hung.entity;

import java.util.List;

/**
 * 分页对象
 * @author Hung
 */
public class PageBean<T> {
    /**
     * 总记录数，总页数，返回集合，当前页码,每页显示记录数
     */
    private int totalCount;
    private int totalPage;
    private List<T> list;
    private int currentPage;
    private int rows;

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    @Override
    public String toString() {
        return "{" +
                "\"totalCount\":\"" + totalCount +
                "\", \"totalPage\":\"" + totalPage +
                "\", \"list\":\"" + list +
                "\", \"currentPage\":\"" + currentPage +
                "\", \"rows\":\"" + rows +
                '}';
    }
}
