package com.example.administrator.htmlparser.presenter;

import android.content.Intent;
import android.support.v4.app.Fragment;

import com.example.administrator.htmlparser.Fragment.IBaseView;

/**
 * Created by Administrator on 2017/7/26.
 */

public class BaseFragmentPresenter {
    /**
     * 碎片
     */
    protected Fragment fragment;
    public BaseFragmentPresenter(IBaseView baseView){
        fragment=(Fragment) baseView;
    }
    /**
     * 打开Activity
     *
     * @param cls 目标class
     */
    public void startActivity(Class<?> cls) {
        fragment.startActivity(new Intent(fragment.getActivity(),cls));
    }
}
