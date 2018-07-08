package com.kumar.rahul.wikiformoneytap.pojo;

/**
 * @author Rahul Kumar on 08.07.2018.
 * @version 1.0
 *
 **/

public class WikiResult {
    private String title;
    private String thumbnail;
    private String description;
    private String pageid;

    public WikiResult(String title, String thumbnail, String description,String pageid) {
        this.title = title;
        this.thumbnail = thumbnail;
        this.description = description;
        this.pageid=pageid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPageid() {
        return pageid;
    }

    public void setPageid(String pageid) {
        this.pageid = pageid;
    }
}