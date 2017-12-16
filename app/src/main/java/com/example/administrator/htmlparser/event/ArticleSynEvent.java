package com.example.administrator.htmlparser.event;

import org.jsoup.select.Elements;

/**
 * Created by Administrator on 2017/7/24.
 */

public class ArticleSynEvent {
    private int count;
    private Elements message;
private String url;
    /**
     * @param mesasge UID
     */
    public ArticleSynEvent(Elements mesasge, int count) {
        this.message=mesasge;
        this.count=count;
    }
    public ArticleSynEvent(String url, int count) {
        this.url=url;
        this.count=count;
    }

    public int getCount() {
        return count;
    }
    public Elements getMesasge(){
        return message;
    }
    public String getUrl(){
        return url;
    }
    public void setCount(int count) {
        this.count = count;
    }
}
