package com.example.administrator.htmlparser.presenter.interf;

import com.example.administrator.htmlparser.Fragment.impl.IFragmentVolunteerService;
import com.example.administrator.htmlparser.model.facytory.CivilizationFocusingModelFactory;
import com.example.administrator.htmlparser.model.impl.ICivilizationFocusingModel;
import com.example.administrator.htmlparser.presenter.BaseFragmentPresenter;
import com.example.administrator.htmlparser.presenter.impl.IVolunteerServicePresenter;

/**
 * Created by Administrator on 2017/7/26.
 */

public class VolunteerServicePresenter extends BaseFragmentPresenter implements IVolunteerServicePresenter{
    private IFragmentVolunteerService fragment;
    private ICivilizationFocusingModel model;
    public VolunteerServicePresenter(IFragmentVolunteerService fragmentVolunteerService) {
        super(fragmentVolunteerService);
        this.fragment=fragmentVolunteerService;
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
    }
}
