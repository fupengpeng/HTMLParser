package com.example.administrator.htmlparser.entity;

/**
 * Created by Administrator on 2017/7/21.
 */

public class News {
    private String newsTime;
    private String newsUrl;
    private String newsTitle;

    public News() {

    }

    public News(String newsTitle, String newsTime, String newsUrl) {
        this.newsTime = newsTime;
        this.newsUrl = newsUrl;
        this.newsTitle = newsTitle;
    }

    public String getNewsTime() {
        return newsTime;
    }

    public void setNewsTime(String newsTime) {
        this.newsTime = newsTime;
    }

    public String getNewsUrl() {
        return newsUrl;
    }

    public void setNewsUrl(String newsUrl) {
        this.newsUrl = newsUrl;
    }

    public String getNewsTitle() {
        return newsTitle;
    }

    public void setNewsTitle(String newsTitle) {
        this.newsTitle = newsTitle;
    }

}
