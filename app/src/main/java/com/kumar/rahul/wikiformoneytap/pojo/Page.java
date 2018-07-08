
package com.kumar.rahul.wikiformoneytap.pojo;

/**
 * @author Rahul Kumar on 08.07.2018.
 * @version 1.0
 *
 **/

public class Page {

    private Integer pageid;
    private Integer ns;
    private String title;
    private Integer index;
    private Terms terms;
    private Thumbnail thumbnail;
    private String pageimage;

    public Page(Integer pageid, Integer ns, String title, Integer index, Terms terms, Thumbnail thumbnail, String pageimage) {
        this.pageid = pageid;
        this.ns = ns;
        this.title = title;
        this.index = index;
        this.terms = terms;
        this.thumbnail = thumbnail;
        this.pageimage = pageimage;
    }

    public Integer getPageid() {
        return pageid;
    }

    public void setPageid(Integer pageid) {
        this.pageid = pageid;
    }

    public Integer getNs() {
        return ns;
    }

    public void setNs(Integer ns) {
        this.ns = ns;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public Terms getTerms() {
        return terms;
    }

    public void setTerms(Terms terms) {
        this.terms = terms;
    }

    public Thumbnail getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(Thumbnail thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getPageimage() {
        return pageimage;
    }

    public void setPageimage(String pageimage) {
        this.pageimage = pageimage;
    }

}
