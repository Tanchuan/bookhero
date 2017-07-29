package com.book.core.model;

import java.util.Date;

public class BookHeroEvent {
    private Integer id;

    private String title;

    private Date startTime;

    private Date endTime;

    private String address;

    private String eventUrl;

    private Integer status;

    private Date createTime;

    private Date updateTime;


    public BookHeroEvent() {
        this.status = 1;
    }

    public BookHeroEvent(String title, Date startTime, Date endTime, String address, String eventUrl) {
        this();
        this.title = title;
        this.startTime = startTime;
        this.endTime = endTime;
        this.address = address;
        this.eventUrl = eventUrl;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public String getEventUrl() {
        return eventUrl;
    }

    public void setEventUrl(String eventUrl) {
        this.eventUrl = eventUrl == null ? null : eventUrl.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}