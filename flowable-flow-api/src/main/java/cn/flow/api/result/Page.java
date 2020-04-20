package cn.flow.api.result;

import java.util.List;

public class Page<T> {
    private Long count;
    private Integer pageSize;
    private Integer pageNum;
    private Integer maxPage;
    private List<T> objList;

    public Page() {
    }

    public Long getCount() {
        return this.count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public Integer getPageSize() {
        return this.pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getPageNum() {
        return this.pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getMaxPage() {
        return this.maxPage;
    }

    public void setMaxPage(Integer maxPage) {
        this.maxPage = maxPage;
    }

    public List<T> getObjList() {
        return this.objList;
    }

    public void setObjList(List<T> objList) {
        this.objList = objList;
    }
}
