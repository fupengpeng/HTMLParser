package com.example.administrator.htmlparser.presenter.factory;

import com.example.administrator.htmlparser.Fragment.impl.IFragmentCivilizationFocusing;
import com.example.administrator.htmlparser.presenter.impl.ICivilizationFocusingPresenter;
import com.example.administrator.htmlparser.presenter.interf.CivilizationFocusingPresenter;

/**
 * Created by Administrator on 2017/7/26.
 */

public class CivilizationFocusingPresenterFactory {
    public static ICivilizationFocusingPresenter newHome(IFragmentCivilizationFocusing fragmentCivilizationFocusing){
        return new CivilizationFocusingPresenter(fragmentCivilizationFocusing);
    }

}
