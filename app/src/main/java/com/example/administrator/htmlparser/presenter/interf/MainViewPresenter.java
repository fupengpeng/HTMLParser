package com.example.administrator.htmlparser.presenter.interf;

import com.example.administrator.htmlparser.entity.Data;
import com.example.administrator.htmlparser.activity.impl.IMainView;
import com.example.administrator.htmlparser.model.facytory.CivilizationFocusingModelFactory;
import com.example.administrator.htmlparser.model.impl.ICivilizationFocusingModel;
import com.example.administrator.htmlparser.presenter.BaseActivityPresenter;
import com.example.administrator.htmlparser.presenter.impl.IMainViewPresenter;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/7/27.
 */

public class MainViewPresenter extends BaseActivityPresenter implements IMainViewPresenter{
    private IMainView view;
    private ICivilizationFocusingModel model;
    public MainViewPresenter(IMainView view) {
        super(view);
        this.view=view;
        this.model= CivilizationFocusingModelFactory.newHome();
    }

    /**
     * 初始化数据
     * @param url
     */
    @Override
    public void init(final String url) {
        new Thread(){
            @Override
            public void run() {
                super.run();
                model.init(url);
            }
        }.start();
    }

    /**
     * 加载更多数据
     * @param url
     * @param y
     * @param data
     */
    @Override
    public void loading(final String url, final int y, final ArrayList<Data> data) {
        new Thread(){
            @Override
            public void run() {
                super.run();
                model.loading(url,y,data);
            }
        }.start();
    }

    /**
     * 刷新
     * @param url
     * @param data
     */
    @Override
    public void refresh(final String url, final ArrayList<Data> data) {
        new Thread(){
            @Override
            public void run() {
                super.run();
                model.refresh(url,data);
            }
        }.start();
    }
}
