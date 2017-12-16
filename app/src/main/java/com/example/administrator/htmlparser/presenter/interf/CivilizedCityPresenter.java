package com.example.administrator.htmlparser.presenter.interf;

import com.example.administrator.htmlparser.Fragment.impl.IFragmentCivilizedCity;
import com.example.administrator.htmlparser.model.facytory.CivilizationFocusingModelFactory;
import com.example.administrator.htmlparser.model.impl.ICivilizationFocusingModel;
import com.example.administrator.htmlparser.presenter.BaseFragmentPresenter;
import com.example.administrator.htmlparser.presenter.impl.ICivilizedCityPresenter;

/**
 * Created by Administrator on 2017/7/26.
 */

public class CivilizedCityPresenter extends BaseFragmentPresenter implements ICivilizedCityPresenter{
    private IFragmentCivilizedCity fragment;
    private ICivilizationFocusingModel model;
    public CivilizedCityPresenter(IFragmentCivilizedCity fragmentCivilizedCity) {
        super(fragmentCivilizedCity);
        this.fragment=fragmentCivilizedCity;
        this.model= CivilizationFocusingModelFactory.newHome();
    }

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

    @Override
    public void loading(final String url) {
        new Thread(){
            @Override
            public void run() {
                super.run();
//                model.loading(url, y, data);
            }
        }.start();
    }

    @Override
    public void refresh(final String url) {
        new Thread(){
            @Override
            public void run() {
                super.run();
                model.init(url);
            }
        }.start();
//        model.refresh(url, data);
    }
}
