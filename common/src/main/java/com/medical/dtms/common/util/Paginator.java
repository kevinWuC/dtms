package com.medical.dtms.common.util;

import java.io.Serializable;
import java.util.List;

/**
 * 分页器
 *
 * @author masai
 * @version $Id: Paginator.java, v 0.1 2017年5月11日 下午3:30:06 masai Exp $
 */
public class Paginator<T> implements Serializable {

    /** 默认页码 */
    public static final int   DEFAULT_PAGE_NO   = 1;
    /** 默认每页条数 */
    public static final int   DEFAULT_PAGE_SIZE = 10;

    /** 当前页码 */
    private int               pageNum;

    /** 每页条数 */
    private int               pageSize;

    /** 总条数 */
    private long              total;

    /** 总页数 */
    private int               pages;

    /** 当前页数据 */
    private List<T> list;

    /**
     * 构造函数
     *
     */
    public Paginator() {
        pageNum = DEFAULT_PAGE_NO;
        pageSize = DEFAULT_PAGE_SIZE;
        total = 0;
        pages = 0;
    }

    /**
     * 构造函数
     *
     * @param pageNum
     * @param pageSize
     * @param total
     */
    public Paginator(int pageNum, int pageSize, long total) {
        this.pageNum = pageNum;
        this.pageSize = pageSize == 0 ? DEFAULT_PAGE_SIZE : pageSize;
        this.total = total;
        this.pages = (int) ((total + pageSize - 1) / pageSize);
    }

    /**
     * 构造函数
     *
     * @param pageNum
     * @param pageSize
     * @param total
     * @param list
     */
    public Paginator(int pageNum, int pageSize, long total, List<T> list) {
        this.pageNum = pageNum;
        this.pageSize = pageSize == 0 ? DEFAULT_PAGE_SIZE : pageSize;
        this.total = total;
        this.pages = (int) ((total + pageSize - 1) / pageSize);
        this.list = list;
    }

    public int getpageNum() {
        return pageNum;
    }

    public void setpageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize == 0 ? DEFAULT_PAGE_SIZE : pageSize;
        this.pages = (int) ((total + pageSize - 1) / pageSize);
    }

    public long gettotal() {
        return total;
    }

    public void settotal(long total) {
        this.total = total;
        pageSize = this.pageSize == 0 ? DEFAULT_PAGE_SIZE : this.pageSize;
        this.pages = (int) ((total + pageSize - 1) / pageSize);
    }

    public int getpages() {
        return pages;
    }

    public List<T> getlist() {
        return list;
    }

    public void setlist(List<T> list) {
        this.list = list;
    }

    public void setpages(int pages) {
        this.pages = pages;
    }
}
