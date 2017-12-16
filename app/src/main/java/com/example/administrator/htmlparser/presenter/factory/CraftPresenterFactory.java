package com.example.administrator.htmlparser.presenter.factory;

import com.example.administrator.htmlparser.Fragment.impl.IFragmentCraft;
import com.example.administrator.htmlparser.presenter.impl.ICraftPresenter;
import com.example.administrator.htmlparser.presenter.interf.CraftPresenter;

/**
 * Created by Administrator on 2017/7/26.
 */

public class CraftPresenterFactory {
    public static ICraftPresenter newHome(IFragmentCraft fragmentCraft){
        return new CraftPresenter(fragmentCraft);
    }
}
