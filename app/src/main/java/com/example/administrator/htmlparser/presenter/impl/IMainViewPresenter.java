package com.example.administrator.htmlparser.presenter.impl;

import com.example.administrator.htmlparser.entity.Data;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/7/27.
 */

public interface IMainViewPresenter {
    void init(String url);
    void loading(String url, int y, ArrayList<Data> data);
    void refresh(String url, ArrayList<Data> data);
}
