package com.example.administrator.htmlparser.presenter.factory;

import com.example.administrator.htmlparser.Fragment.impl.IFragmentCivilizedCity;
import com.example.administrator.htmlparser.presenter.impl.ICivilizedCityPresenter;
import com.example.administrator.htmlparser.presenter.interf.CivilizedCityPresenter;

/**
 * Created by Administrator on 2017/7/26.
 */

public class CivilizedCityPresenterFactory {


    public static ICivilizedCityPresenter newHome (IFragmentCivilizedCity fragmentCivilizedCity){
        return new CivilizedCityPresenter(fragmentCivilizedCity);
    }
}
