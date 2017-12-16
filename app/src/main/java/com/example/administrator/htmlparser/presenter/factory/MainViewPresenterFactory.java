package com.example.administrator.htmlparser.presenter.factory;

import com.example.administrator.htmlparser.activity.impl.IMainView;
import com.example.administrator.htmlparser.presenter.impl.IMainViewPresenter;
import com.example.administrator.htmlparser.presenter.interf.MainViewPresenter;

/**
 * Created by Administrator on 2017/7/27.
 */

public class MainViewPresenterFactory {
    public static IMainViewPresenter newHome(IMainView view){
        return new MainViewPresenter(view);
    }
}
