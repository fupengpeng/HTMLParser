package com.example.administrator.htmlparser.activity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.widget.SlidingPaneLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;

import com.example.administrator.htmlparser.R;

import java.lang.reflect.Field;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2017/7/24.
 */

public class BaseActivity extends AppCompatActivity implements SlidingPaneLayout.PanelSlideListener {
    /**
     * 用于解除ButterKnife绑定
     */
    private Unbinder unbinder;

    public void onCreate(Bundle savedInstanceState, int layoutResId) {
        initSwipeBackFinish();
//        EventBus.getDefault().register(this);//订阅
        super.onCreate(savedInstanceState);
        setContentView(layoutResId);
        //透明状态栏
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // 透明状态栏
            getWindow().addFlags(
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//             透明通知栏
//            getWindow().addFlags(
//                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
        // ButterKnife绑定
        unbinder = ButterKnife.bind(this);

    }
    private void initSwipeBackFinish() {
        if (isSupportSwipeBack()){
            SlidingPaneLayout slidingPaneLayout=new SlidingPaneLayout(this);
            try {
                Field f_overHang=SlidingPaneLayout.class.getDeclaredField("mOverhangSize");
                f_overHang.setAccessible(true);
                f_overHang.set(slidingPaneLayout,0);
            } catch (Exception e) {
                e.printStackTrace();
            }
            slidingPaneLayout.setPanelSlideListener(this);
            slidingPaneLayout.setSliderFadeColor(getResources().getColor(android.R.color.transparent));
            View leftView=new View(this);
            leftView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT));
            slidingPaneLayout.addView(leftView,0);
            ViewGroup decor= (ViewGroup) getWindow().getDecorView();
            ViewGroup decorChild = (ViewGroup) decor.getChildAt(0);
            decorChild.setBackgroundColor(getResources().getColor(android.R.color.white));
            decor.removeView(decorChild);
            decor.addView(slidingPaneLayout);
            slidingPaneLayout.addView(decorChild,1);
        }
    }
    protected boolean isSupportSwipeBack(){
        return true;
    }
        /**
         * 打开Activity
         *
         * @param cls 目标class
         */
    public void startActivity(Class<?> cls) {
        startActivity(new Intent(this, cls));
    }

    public void startWeb(String url){
        startActivity(new Intent("android.intent.action.VIEW").setData(Uri.parse(url)));
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        EditText editText;
//        editText.setText("");
        // 解除ButterKnife绑定
        if (unbinder != null) {
            unbinder.unbind();
        }
//        EventBus.getDefault().unregister(this);//解除订阅

    }
    public boolean isNetworkConnected() {

        ConnectivityManager connectivity = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo info = connectivity.getActiveNetworkInfo();
            if (info != null && info.isConnected())
            {
                // 当前网络是连接的
                if (info.getState() == NetworkInfo.State.CONNECTED)
                {
                    Log.e("--","www");
                    // 当前所连接的网络可用
                    return true;
                }

            }
        }
        return false;

    }

    @Override
    public void onPanelSlide(View panel, float slideOffset) {

    }

    @Override
    public void onPanelOpened(View panel) {
        finish();
        this.overridePendingTransition(0, R.anim.slide_out_right);
    }

    @Override
    public void onPanelClosed(View panel) {

    }
}
