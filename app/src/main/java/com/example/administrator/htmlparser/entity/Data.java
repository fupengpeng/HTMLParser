package com.example.administrator.htmlparser.entity;

/**
 * Created by Administrator on 2017/7/24.
 */

public class Data {
    private String title;
    private String url;

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

    public Data(String title, String url) {
        this.title = title;
        this.url = url;
    }

    @Override
    public String toString() {
        return "Data{" +
                "title='" + title + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
