package com.example.administrator.htmlparser.model.impl;

import com.example.administrator.htmlparser.entity.Data;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/7/26.
 */

public interface ICivilizationFocusingModel {
    void init(String url);
    void loading(String url, int y, ArrayList<Data> data);
    void refresh(String url, ArrayList<Data> data);
    void Img(String url);
}

