package com.example.administrator.htmlparser.presenter.interf;

import com.example.administrator.htmlparser.Fragment.impl.IFragmentCraft;
import com.example.administrator.htmlparser.model.facytory.CivilizationFocusingModelFactory;
import com.example.administrator.htmlparser.model.impl.ICivilizationFocusingModel;
import com.example.administrator.htmlparser.presenter.BaseFragmentPresenter;
import com.example.administrator.htmlparser.presenter.impl.ICraftPresenter;

/**
 * Created by Administrator on 2017/7/26.
 */

public class CraftPresenter extends BaseFragmentPresenter implements ICraftPresenter{
    private IFragmentCraft fragment;
    private ICivilizationFocusingModel model;
    public CraftPresenter(IFragmentCraft fragment) {
        super(fragment);
        this.fragment=fragment;
        this.model= CivilizationFocusingModelFactory.newHome();
    }

    @Override
    public void init(final String craft) {
        new Thread(){
            @Override
            public void run() {
                super.run();
                model.Img(craft);
            }
        }.start();
    }
}
