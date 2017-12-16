package com.example.administrator.htmlparser.presenter.factory;

import com.example.administrator.htmlparser.Fragment.impl.IFragmentVolunteerService;
import com.example.administrator.htmlparser.presenter.impl.IVolunteerServicePresenter;
import com.example.administrator.htmlparser.presenter.interf.VolunteerServicePresenter;

/**
 * Created by Administrator on 2017/7/26.
 */

public class VolunteerServicePresenterFactory {
    public static IVolunteerServicePresenter newHome (IFragmentVolunteerService fragmentVolunteerService){
        return new VolunteerServicePresenter(fragmentVolunteerService);
    }
}
