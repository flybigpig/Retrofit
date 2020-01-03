package com.example.baidu.retrofit.Bean.home;

import java.util.Date;
import java.util.List;

/**
 * @author
 * @date 2020/1/3.
 * GitHub：
 * email：
 * description：
 */
public class AndroidBean {

    private String Id;
    private Date createdat;
    private String desc;
    private List<String> images;
    private Date publishedat;
    private String source;
    private String type;
    private String url;
    private boolean used;
    private String who;


    public void setId(String Id) {
        this.Id = Id;
    }
    public String getId() {
        return Id;
    }


    public void setCreatedat(Date createdat) {
        this.createdat = createdat;
    }
    public Date getCreatedat() {
        return createdat;
    }


    public void setDesc(String desc) {
        this.desc = desc;
    }
    public String getDesc() {
        return desc;
    }


    public void setImages(List<String> images) {
        this.images = images;
    }
    public List<String> getImages() {
        return images;
    }


    public void setPublishedat(Date publishedat) {
        this.publishedat = publishedat;
    }
    public Date getPublishedat() {
        return publishedat;
    }


    public void setSource(String source) {
        this.source = source;
    }
    public String getSource() {
        return source;
    }


    public void setType(String type) {
        this.type = type;
    }
    public String getType() {
        return type;
    }


    public void setUrl(String url) {
        this.url = url;
    }
    public String getUrl() {
        return url;
    }


    public void setUsed(boolean used) {
        this.used = used;
    }
    public boolean getUsed() {
        return used;
    }


    public void setWho(String who) {
        this.who = who;
    }
    public String getWho() {
        return who;
    }
}
