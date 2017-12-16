package com.example.administrator.htmlparser.presenter.factory;

import com.example.administrator.htmlparser.Fragment.impl.IFragmentCharmSchool;
import com.example.administrator.htmlparser.presenter.impl.ICharmSchoolPresenter;
import com.example.administrator.htmlparser.presenter.interf.CharmSchoolPresenter;

/**
 * Created by Administrator on 2017/7/26.
 */

public class CharmSchoolPresenterFactory {
    public static ICharmSchoolPresenter newHome(IFragmentCharmSchool fragmentCharmSchool){
        return new CharmSchoolPresenter(fragmentCharmSchool);
    }
}
