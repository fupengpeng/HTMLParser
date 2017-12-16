package com.example.administrator.htmlparser.event;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/7/26.
 */

public class DataMassageSynEvent {
    private int count;
    private ArrayList message;

    /**
     * @param uid UID
     */
    public DataMassageSynEvent(ArrayList uid, int count) {
        this.message=uid;
        this.count=count;
    }

    public int getCount() {
        return count;
    }
    public ArrayList getMessage(){
        return message;
    }
    public void setCount(int count) {
        this.count = count;
    }
}
