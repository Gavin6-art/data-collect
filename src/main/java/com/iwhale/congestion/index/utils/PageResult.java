package com.iwhale.congestion.index.utils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author chen.jinshu
 *         2018/06/27
 */
public class PageResult<T> implements Serializable {
    private static final long serialVersionUID = 3012691672208664300L;
    private int count;
    private List<T> list = new ArrayList();
    private String orderBy = "";
    private Integer pageNumber = Integer.valueOf(1);
    private Integer pageSize = Integer.valueOf(10);

    public int getMysqlIndex() {
        return (this.pageNumber.intValue() - 1) * this.pageSize.intValue();
    }

    public PageResult(Integer pageNumber, Integer pageSize) {
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
    }

    public int getCount() {
        return this.count;
    }

    public List<T> getList() {
        return this.list;
    }

    public String getOrderBy() {
        return this.orderBy;
    }

    public int getPageNumber() {
        return this.pageNumber.intValue();
    }

    public int getPageSize() {
        return this.pageSize.intValue();
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}