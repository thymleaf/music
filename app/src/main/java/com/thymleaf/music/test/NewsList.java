package com.thymleaf.music.test;

import java.util.List;

public class NewsList
{


    /**
     * totalCount : 4
     * pageSize : 10
     * totalPage : 1
     * currPage : 1
     * list : []
     */

    private int totalCount;
    private int pageSize;
    private int totalPage;
    private int currPage;
    private List<News> list;

    public int getTotalCount() { return totalCount;}

    public void setTotalCount(int totalCount) { this.totalCount = totalCount;}

    public int getPageSize() { return pageSize;}

    public void setPageSize(int pageSize) { this.pageSize = pageSize;}

    public int getTotalPage() { return totalPage;}

    public void setTotalPage(int totalPage) { this.totalPage = totalPage;}

    public int getCurrPage() { return currPage;}

    public void setCurrPage(int currPage) { this.currPage = currPage;}

    public List<News> getList() { return list;}

    public void setList(List<News> list) { this.list = list;}
}
