package com.example.administrator.htmlparser.Fragment;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2017/7/26.
 * 碎片父类
 */

public abstract class BaseFragment extends Fragment{
    Unbinder unbinder;
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, int layoutResId) {
        View view = inflater.inflate(layoutResId, container, false);
        //绑定ButterKnife
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    /**
     * 打开Activity
     *
     * @param cls 目标class
     */
    public void startActivity(Class<?> cls) {
        startActivity(new Intent(getActivity(), cls));
    }
public void startWeb(String url){
    startActivity(new Intent("android.intent.action.VIEW").setData(Uri.parse(url)));
}
    @Override
    public void onDestroy() {
        super.onDestroy();
        // 解除ButterKnife绑定
        if (unbinder != null) {
            unbinder.unbind();
        }
    }
}
