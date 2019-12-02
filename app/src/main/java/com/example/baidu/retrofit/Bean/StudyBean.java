package com.example.baidu.retrofit.Bean;

/**
 * 学习内容

 */
public class StudyBean {

    private String title;

    private String url;

    private String content;

    public StudyBean(String title, String content, String url) {
        this.title=title;
        this.content=content;
        this.url=url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
