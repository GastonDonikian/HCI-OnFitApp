package com.example.hci_onfitapp.api.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class PagedList<T> {
    @Expose
    @SerializedName("totalCount")
    private Integer totalCount;

    @Expose
    @SerializedName("orderBy")
    private String orderBy;

    @Expose
    @SerializedName("direction")
    private String direction;

    @Expose
    @SerializedName("content")
    private List<T> content;

    @Expose
    @SerializedName("size")
    private Integer size;

    @Expose
    @SerializedName("page")
    private Integer page;

    @Expose
    @SerializedName("isLastPage")
    private Boolean isLastPage;

    public PagedList(Integer totalCount, String orderBy, String direction, ArrayList<T> content, Integer size, Integer page, Boolean isLastPage) {
        this.totalCount = totalCount;
        this.orderBy = orderBy;
        this.direction = direction;
        this.content = content;
        this.size = size;
        this.page = page;
        this.isLastPage = isLastPage;
    }

    public PagedList(){

    }

    public Integer getTotalCount() {
        return totalCount;
    }

    public String getOrderBy() {
        return orderBy;
    }

    public String getDirection() {
        return direction;
    }

    public List<T> getContent() {
        return content;
    }

    public Integer getSize() {
        return size;
    }

    public Integer getPage() {
        return page;
    }

    public Boolean getLastPage() {
        return isLastPage;
    }

    @Override
    public String toString() {
        return "PagedList{" +
                "totalCount=" + totalCount +
                ", orderBy='" + orderBy + '\'' +
                ", direction='" + direction + '\'' +
                ", content=" + content +
                ", size=" + size +
                ", page=" + page +
                ", isLastPage=" + isLastPage +
                '}';
    }
}
