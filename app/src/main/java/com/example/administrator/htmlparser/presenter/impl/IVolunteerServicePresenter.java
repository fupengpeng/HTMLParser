package com.example.administrator.htmlparser.presenter.impl;

/**
 * Created by Administrator on 2017/7/26.
 */

public interface IVolunteerServicePresenter {
    void init(String url);
    void loading(String url);
    void refresh(String url);
}
