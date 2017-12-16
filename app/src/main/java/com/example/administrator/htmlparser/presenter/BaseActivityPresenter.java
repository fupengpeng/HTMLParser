package com.example.administrator.htmlparser.presenter;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;

import com.example.administrator.htmlparser.Fragment.IBaseView;

/**
 * Created by Administrator on 2017/6/8.
 */

public class BaseActivityPresenter {

    /**
     * 网络请求时的等待对话框
     */
    private Dialog dialog = null;
    /**
     * Activity
     */
    protected Activity activity;
    public BaseActivityPresenter(IBaseView baseView) {
        activity = (Activity) baseView;

    }

    /**
     * 关闭等待对话框
     */
    public void closeWaitDialog() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }
    /**
     * 打开Activity
     *
     * @param cls 目标class
     */
    public void startActivity(Class<?> cls) {

        activity.startActivity(new Intent(activity, cls));
    }
}
