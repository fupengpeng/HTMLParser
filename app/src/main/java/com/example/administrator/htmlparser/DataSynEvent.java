package com.example.administrator.htmlparser;

import com.example.administrator.htmlparser.entity.Data;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/7/10.
 */

public class DataSynEvent {
    private int count;
    private String message;
    private ArrayList<Data> messages;
    private int currentPage;
    String[] messageCraft;


    /**
     * @param uid UID
     */
    public DataSynEvent(String uid, int count) {
        this.message=uid;
        this.count=count;
    }
    public DataSynEvent(ArrayList<Data> messages, int count) {
        this.messages=messages;
        this.count=count;
    }
    public DataSynEvent(int currentPage, int count) {
        this.currentPage=currentPage;
        this.count=count;
    }
    public DataSynEvent(String[] messageCraft, int count) {
        this.messageCraft=messageCraft;
        this.count=count;
    }


    public DataSynEvent(int count , String message , ArrayList<Data> messages){
        this.count = count;
        this.message = message;
        this.messages = messages;
    }



    public int getCount() {
        return count;
    }
    public String getMessage(){
        return message;
    }
    public ArrayList<Data> getMessages(){
        return messages;
    }
    public int getCurrentPage(){
        return currentPage;
    }

    public String[] getMessageCraft() {
        return messageCraft;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
